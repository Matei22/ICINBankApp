import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import {ActivatedRoute, Router} from "@angular/router";
import { catchError, Observable, throwError } from 'rxjs';
import { AccountDetails } from '../model/account.model';
import {Customer} from "../model/customer.model";
import { TransferRequests } from '../model/transfer-requests';
import { AccountsService } from '../services/accounts.service';

@Component({
  selector: 'app-customer-accounts',
  templateUrl: './customer-accounts.component.html',
  styleUrls: ['./customer-accounts.component.css']
})
export class CustomerAccountsComponent implements OnInit {
  transferRequest : TransferRequests;
  accountFormGroup! : FormGroup;
  currentPage : number =0;
  pageSize : number =5;
  accountObservable! : Observable<AccountDetails>
  operationFromGroup! : FormGroup;
  errorMessage! :string ;
  customerId! : string ;
  customer! : Customer;
  constructor(private route : ActivatedRoute, private router :Router, private fb : FormBuilder, private accountService : AccountsService) {
    this.customer=this.router.getCurrentNavigation()?.extras.state as Customer;
  }

  ngOnInit(): void {
    this.customerId = this.route.snapshot.params['emailId'];

    this.accountFormGroup=this.fb.group({
      accountId : this.fb.control('')
    });
    this.operationFromGroup=this.fb.group({
      operationType : this.fb.control(null),
      amount : this.fb.control(0),
      description : this.fb.control(null),
      accountDestination : this.fb.control(null)
    })
  }
  handleSearchAccount() {
    let accountId : string =this.accountFormGroup.value.accountId;
    this.accountObservable=this.accountService.getAccountsFromCustomer(this.customer.emailId, accountId,this.currentPage, this.pageSize).pipe(
      catchError(err => {
        this.errorMessage=err.message;
        return throwError(err);
      })
    );
  }

  gotoPage(page: number) {
    this.currentPage=page;
    this.handleSearchAccount();
  }

  handleAccountOperation() {
    let accountId :string = this.accountFormGroup.value.accountId;
    let operationType=this.operationFromGroup.value.operationType;
    let amount :number =this.operationFromGroup.value.amount;
    let description :string =this.operationFromGroup.value.description;
    let accountDestination :string =this.operationFromGroup.value.accountDestination;
    let status:string = "PENDING";
    if(operationType=='DEBIT'){
      this.accountService.debit(status, accountId, amount, description).subscribe({
        next : ()=>{
          alert("Success Credit");
          this.operationFromGroup.reset();
          this.handleSearchAccount();
        },
        error : (err)=>{
          console.log(err);
        }
      });
    } else if(operationType=='CREDIT'){
      this.accountService.credit(status, accountId, amount,description).subscribe({
        next : ()=>{
          alert("Success Debit");
          this.operationFromGroup.reset();
          this.handleSearchAccount();
        },
        error : (err)=>{
          console.log(err);
        }
      });
    }
    else if(operationType=='TRANSFER'){
      this.accountService.transfer(status, accountId,accountDestination, amount,description).subscribe({
        next : (data)=>{
          
          this.transferRequest = data;
          console.log(this.transferRequest.status)
          alert("Success Transfer");
          this.operationFromGroup.reset();
          this.handleSearchAccount();
        },
        error : (err)=>{
          console.log(err);
        }
      });

    }
    
  }

  handleLogout(){
    this.router.navigateByUrl('/login')
  }

  handleCustomerAccounts(customer: Customer) {
    this.router.navigateByUrl("/customer-accounts/"+customer.emailId,{state :customer});
  }
  handleNewAccount(customer: Customer) {
    this.router.navigateByUrl("/new-account/"+customer.emailId,{state :customer});
  }

}
