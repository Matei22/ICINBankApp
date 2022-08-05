import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AccountDetails, AccountOperationDTOS } from '../model/account.model';
import { Customer } from '../model/customer.model';
import { AccountsService } from '../services/accounts.service';

@Component({
  selector: 'app-new-account-request',
  templateUrl: './new-account-request.component.html',
  styleUrls: ['./new-account-request.component.css']
})
export class NewAccountRequestComponent implements OnInit {

  customerId! : string ;
  customer! : Customer;
  newAccounts : Array<AccountDetails>;
  newAccount:AccountDetails;
  constructor(private route : ActivatedRoute, private router :Router, private fb : FormBuilder, private accountService : AccountsService) {
    this.customer=this.router.getCurrentNavigation()?.extras.state as Customer;
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
