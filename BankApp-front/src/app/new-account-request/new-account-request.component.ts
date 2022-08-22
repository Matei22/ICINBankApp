import { Component, OnInit } from '@angular/core';
import { UntypedFormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AccountDetails, AccountOperationDTOS } from '../model/account';
import { Customer } from '../model/customer';
import { AccountsService } from '../services/accounts.service';
import { CustomerService } from '../services/customer.service';
@Component({
  selector: 'app-new-account-request',
  templateUrl: './new-account-request.component.html',
  styleUrls: ['./new-account-request.component.css']
})
export class NewAccountRequestComponent implements OnInit {

  customerId! : string ;
  customer! : Customer;
  newAccounts : Array<AccountDetails>;
  newAccount : AccountDetails;
  constructor(private route : ActivatedRoute, private router :Router, private fb : UntypedFormBuilder, private accountService : AccountsService, private custServ: CustomerService) {
    this.customerId = this.route.snapshot.params['emailId'];
    this.custServ.getCustomer(this.customerId).subscribe((data)=>{this.customer = data}) 
  }
  ngOnInit(): void {
    this.customerId = this.route.snapshot.params['emailId'];
    this.accountService.getAccountsByStatus(this.customerId, "CREATED").subscribe(
      data=>this.newAccounts=data
    )
  }

  acceptClick(a:AccountDetails){
    let conf = confirm("Are you sure?");
    if(!conf) return;
    this.accountService.updateAccountStatus(this.customerId, a.accId, "ACTIVATED").subscribe(
      result=>this.newAccount=result);
    window.location.reload();
    
  }
  declineClick(a:AccountDetails){
    let conf = confirm("Are you sure?");
    if(!conf) return;
    this.accountService.updateAccountStatus(this.customerId, a.accId, "SUSPENDED").subscribe(
      result=>this.newAccount=result);
    window.location.reload();
  }

}
