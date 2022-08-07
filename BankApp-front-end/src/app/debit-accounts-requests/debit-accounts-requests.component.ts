import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { catchError, Observable, throwError } from 'rxjs';
import { AccountDetails, AccountOperationDTOS } from '../model/account.model';
import { Customer } from '../model/customer.model';
import { AccountsService } from '../services/accounts.service';
import { CustomerService } from '../services/customer.service';

@Component({
  selector: 'app-debit-accounts-requests',
  templateUrl: './debit-accounts-requests.component.html',
  styleUrls: ['./debit-accounts-requests.component.css']
})
export class DebitAccountsRequestsComponent implements OnInit {
  customerId! : string ;
  customer! : Customer;
  debitAccount:AccountOperationDTOS;
  debitAccounts:AccountOperationDTOS[];
  errorMessage: '';
  constructor(private route : ActivatedRoute, private router :Router, private fb : FormBuilder, private accountService : AccountsService, private custServ: CustomerService) {
    this.customerId = this.route.snapshot.params['emailId'];
    this.custServ.getCustomer(this.customerId).subscribe((data)=>{this.customer = data}) 
  }
  ngOnInit(): void {
    this.accountService.getDebitPendingAccounts(this.customerId).subscribe(
      result=>this.debitAccounts=result)
  }
  acceptClick(account:AccountOperationDTOS){
    let conf = confirm("Are you sure?");
    if(!conf) return;
    this.accountService.updateOperationStatusToAcceptedDeb(account.operationId).subscribe(
      result=>this.debitAccount=result);
    window.location.reload();
  }

  declineClick(account:AccountOperationDTOS){
    let conf = confirm("Are you sure?");
    if(!conf) return;
    this.accountService.updateOperationStatusToDeniedDeb(account.operationId).subscribe(
      result=>this.debitAccount=result);
    window.location.reload();
  }
} 
