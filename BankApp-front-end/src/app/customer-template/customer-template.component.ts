import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { catchError, ObjectUnsubscribedError, Observable, throwError } from 'rxjs';
import { Customer } from '../model/customer.model';
import { CustomerService } from '../services/customer.service';

@Component({
  selector: 'app-customer-template',
  templateUrl: './customer-template.component.html',
  styleUrls: ['./customer-template.component.css']
})
export class CustomerTemplateComponent implements OnInit {

  customer! : Customer;
  customerId : string ;
  errorMessage: string;


  constructor(private route : ActivatedRoute, private router :Router, private custServ: CustomerService) {
    this.customer=this.router.getCurrentNavigation()?.extras.state as Customer;
   }

  ngOnInit(): void {
    this.customerId = this.route.snapshot.params['emailId'];
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
}
