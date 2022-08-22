import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Customer } from '../model/customer';
import { TransferRequests } from '../model/transfer-requests';
import { AccountsService } from '../services/accounts.service';
import { CustomerService } from '../services/customer.service';
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
  constructor(private route : ActivatedRoute, private router :Router, private accountService : AccountsService, private custServ: CustomerService) {
    this.customerId = this.route.snapshot.params['emailId'];
    this.custServ.getCustomer(this.customerId).subscribe((data)=>{this.customer = data}) 
   }
  ngOnInit(): void {
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
