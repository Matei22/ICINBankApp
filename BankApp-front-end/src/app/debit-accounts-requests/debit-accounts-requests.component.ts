import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { catchError, Observable, throwError } from 'rxjs';
import { AccountDetails, AccountOperationDTOS } from '../model/account.model';
import { Customer } from '../model/customer.model';
import { AccountsService } from '../services/accounts.service';

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
  constructor(private route : ActivatedRoute, private router :Router, private fb : FormBuilder, private accountService : AccountsService) {
    this.customer=this.router.getCurrentNavigation()?.extras.state as Customer;
  }
  ngOnInit(): void {
    this.customerId = this.route.snapshot.params['emailId'];
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
