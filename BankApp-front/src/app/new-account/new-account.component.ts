import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Customer } from '../model/customer';
import { AccountsService } from '../services/accounts.service';
import {v4 as uuidv4} from 'uuid';
import { AccountDetails } from '../model/account';
import { CustomerService } from '../services/customer.service';
@Component({
  selector: 'app-new-account',
  templateUrl: './new-account.component.html',
  styleUrls: ['./new-account.component.css']
})
export class NewAccountComponent implements OnInit {
  operationFromGroup! : FormGroup;

  customer! : Customer;
  customerId : string ;
  errorMessage: string;
  account = new AccountDetails();


  constructor(private route : ActivatedRoute, private router :Router, private fb : FormBuilder, private service: AccountsService, private custServ: CustomerService) {
    this.customerId = this.route.snapshot.params['emailId'];
    this.custServ.getCustomer(this.customerId).subscribe((data)=>{this.customer = data}) 
   }

  ngOnInit(): void {
    this.operationFromGroup=this.fb.group({
      balance : this.fb.control(0),
    })
  }
  handleLogout(){
    this.router.navigateByUrl('/login')
  }
  handleCustomerAccounts(customer: Customer) {
    this.router.navigateByUrl("/customer-accounts/"+customer.emailId,{state :customer});
  }
  handleNewAccount(customer: Customer) {
    this.router.navigateByUrl("/new-account/"+customer.emailId,{state :customer});
  }

  newAccounts(customer:Customer){
    let operation=this.operationFromGroup.value.balance;
    let myuuid = uuidv4();
    console.log(myuuid)
    this.account.accId = uuidv4();
    console.log(operation)
    this.account.currentPage=0
    this.account.pageSize=5
    this.account.totalPages=5
    this.account.accountOperationDTOS=[];
    this.service.newAccount(myuuid,operation,"CA",this.customer.emailId).subscribe(
      data => this.account = data
    )
    this.router.navigateByUrl("/customer-accounts/"+customer.emailId,{state :customer});

  }
}
