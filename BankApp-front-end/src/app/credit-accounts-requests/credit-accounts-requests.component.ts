import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AccountOperationDTOS } from '../model/account.model';
import { Customer } from '../model/customer.model';
import { AccountsService } from '../services/accounts.service';

@Component({
  selector: 'app-credit-accounts-requests',
  templateUrl: './credit-accounts-requests.component.html',
  styleUrls: ['./credit-accounts-requests.component.css']
})
export class CreditAccountsRequestsComponent implements OnInit {
  customerId! : string ;
  customer! : Customer;
  creditAccount :AccountOperationDTOS;
  creditAccounts:AccountOperationDTOS[];
  operationDate:Date;
  constructor(private route : ActivatedRoute, private router :Router, private accountService : AccountsService) {
    this.customer=this.router.getCurrentNavigation()?.extras.state as Customer;
  }
  ngOnInit(): void {
    this.customerId = this.route.snapshot.params['emailId'];
    this.accountService.getCreditPendingAccounts(this.customerId).subscribe(
      result=>this.creditAccounts=result)
  }
  acceptClick(account:AccountOperationDTOS){
    let conf = confirm("Are you sure?");
    if(!conf) return;
    this.accountService.updateOperationStatusToAcceptedCred(account.operationId).subscribe(
      result=>this.creditAccount=result);
    window.location.reload();
  }

  declineClick(account:AccountOperationDTOS){
    let conf = confirm("Are you sure?");
    if(!conf) return;
    this.accountService.updateOperationStatusToDeniedCred(account.operationId).subscribe(
      result=>this.creditAccount=result);
    window.location.reload();
  }
}
