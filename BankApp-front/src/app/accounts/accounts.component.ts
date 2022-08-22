import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AccountDetails } from '../model/account';
import { Customer } from '../model/customer';
import { TransferRequests } from '../model/transfer-requests';
import { AccountsService } from '../services/accounts.service';
import { CustomerService } from '../services/customer.service';

@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.css']
})
export class AccountsComponent implements OnInit {

  transferRequest : TransferRequests;
  accountFormGroup! : FormGroup;
  currentPage : number =0;
  pageSize : number =5;
  accountObservable! : AccountDetails
  operationFromGroup! : FormGroup;
  errorMessage! :any ;
  customerId! : string ;
  customer! : Customer;
  constructor(private route : ActivatedRoute, private router :Router, private fb : FormBuilder, private accountService : AccountsService, private customerService:CustomerService) {
    this.customerId = this.route.snapshot.params['emailId'];
   }

  ngOnInit(): void {
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
    this.accountService.getAccount(accountId, this.currentPage, this.pageSize).subscribe(result=>{
      this.accountObservable = result;
    },()=>{
      this.errorMessage="Please provide a correct account id";
    }
    )
  }

  gotoPage(page: number) {
    this.currentPage=page;
    this.handleSearchAccount();
  }

}
