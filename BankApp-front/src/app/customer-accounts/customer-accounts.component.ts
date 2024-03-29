import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router} from "@angular/router";
import { AccountDetails } from '../model/account';
import { Customer} from "../model/customer";
import { TransferRequests } from '../model/transfer-requests';
import { AccountsService } from '../services/accounts.service';
import { CustomerService } from '../services/customer.service';
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
  accountObservable : AccountDetails
  accountTest = new AccountDetails()
  operationFromGroup! : FormGroup;
  errorMessage! :any ;
  customerId! : string ;
  customer! : Customer;
  urlSliced:any;
  constructor(private route : ActivatedRoute, private router :Router, private fb : FormBuilder, private accountService : AccountsService, private customerService:CustomerService) {
    this.customerId = this.route.snapshot.params['emailId'];
   }

  ngOnInit(): void {
    console.log(this.router.url)
    this.urlSliced = this.router.url.slice(1,6)
    this.accountFormGroup=this.fb.group({
      accountId : this.fb.control('', [Validators.required]),
    });
    this.operationFromGroup=this.fb.group({
      operationType : this.fb.control(null),
      amount : this.fb.control(0),
      description : this.fb.control(null),
      accountDestination : this.fb.control(null)
    })
  }
  handleSearchAccount() {
    
    let accountId = this.accountFormGroup.value.accountId;
    this.errorMessage = ''
    this.accountService.getAccount(accountId, this.currentPage, this.pageSize).subscribe(
      data=>this.accountTest.status = data.status
    )
    this.accountService.getAccountsFromCustomer(this.customerId, accountId,this.currentPage, this.pageSize).subscribe({
      next: (result)=>{
      this.accountObservable = result;
    },
      error: ()=>{
      this.errorMessage="Please enter a valid id";
    }
  })
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
          this.operationFromGroup.reset();
          this.handleSearchAccount();
        },
        error : (err)=>{
          console.log(err);
        }
      });

    }
    
  }

}
