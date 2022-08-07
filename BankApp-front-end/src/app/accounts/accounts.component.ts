import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AccountsService} from "../services/accounts.service";
import {catchError, Observable, throwError} from "rxjs";
import {AccountDetails} from "../model/account.model";
import { Customer } from '../model/customer.model';
import { ActivatedRoute, Router } from '@angular/router';
import { CustomerService } from '../services/customer.service';
import { TransferRequests } from '../model/transfer-requests';

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
  operationFromGroup! : FormGroup | undefined;
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
    },(error)=>{
      this.errorMessage=error;
    }
    )
  }

  gotoPage(page: number) {
    this.currentPage=page;
    this.handleSearchAccount();
  }
}
