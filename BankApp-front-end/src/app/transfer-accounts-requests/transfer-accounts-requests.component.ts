import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AccountOperationDTOS } from '../model/account.model';
import { Customer } from '../model/customer.model';
import { TransferRequests } from '../model/transfer-requests';
import { AccountsService } from '../services/accounts.service';

@Component({
  selector: 'app-transfer-accounts-requests',
  templateUrl: './transfer-accounts-requests.component.html',
  styleUrls: ['./transfer-accounts-requests.component.css']
})
export class TransferAccountsRequestsComponent implements OnInit {
  customerId! : string ;
  customer! : Customer;
  transferRequest :TransferRequests;
  transferRequests:TransferRequests[];
  operationDate:Date;
  constructor(private route : ActivatedRoute, private router :Router, private accountService : AccountsService) {
    this.customer=this.router.getCurrentNavigation()?.extras.state as Customer;
  }
  ngOnInit(): void {
    this.customerId = this.route.snapshot.params['emailId'];
    this.accountService.getTransactionsBasedOnCustomer(this.customerId).subscribe(
      result=>this.transferRequests=result)
  }
  acceptClick(transferRequest:TransferRequests){
    let conf = confirm("Are you sure?");
    if(!conf) return;
    this.accountService.updateTransactionStatusToAccepted(transferRequest.transferId).subscribe(
      result=>this.transferRequest=result);
    window.location.reload();
  }

  declineClick(transferRequest:TransferRequests){
    let conf = confirm("Are you sure?");
    if(!conf) return;
    this.accountService.updateTransactionStatusToDenied(transferRequest.transferId).subscribe(
      result=>this.transferRequest=result);
    window.location.reload();
  }

}
