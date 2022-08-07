import { SlicePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Customer } from '../model/customer.model';
import { CustomerService } from '../services/customer.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  customer :Customer;
  customerForNavbar:Observable<Customer>;
  customerId : string ;
  errorMessage: string;
  urlSliced:any


  constructor(private route : ActivatedRoute, private router :Router, private custServ: CustomerService) {
    this.customerId = this.route.snapshot.params['emailId'];
    this.custServ.getCustomer(this.customerId).subscribe(data=>this.customer = data)
    this.customerForNavbar = this.custServ.getCustomer(this.customerId).pipe()
   }

  ngOnInit(): void {
  }
  handleLogout(){
    this.router.navigateByUrl('/login')
    
  }
  handleCustomerAccounts(customer: Customer) {
    this.router.navigateByUrl("/customer-accounts/"+customer.emailId,{state :customer});
  }
  handleNewAccount(customer: Customer) {
    this.urlSliced=this.router.url.slice(1,6)
    this.router.navigateByUrl("/new-account/"+customer.emailId,{state :customer});
  }
}
