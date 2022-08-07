import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RegistrationService } from '../registration.service';
import {Customer} from '../model/customer.model'
import { CustomerService } from '../services/customer.service';
import { Observable } from 'rxjs';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  customer = new Customer();
  loggedInCustomer : Customer;
  msg ='';
  constructor(private service:RegistrationService, private router:Router, private customerService: CustomerService) { }

  ngOnInit(): void {}

  loginUser(){
    this.service.loginUserFormRemote(this.customer).subscribe({
      next: () => {
        this.customerService.getCustomer(this.customer.emailId).subscribe(
          (data)=>{
            this.loggedInCustomer = data;
            if(this.loggedInCustomer.role=="ADMIN"){
              this.router.navigateByUrl("/admin");
            }else if(this.loggedInCustomer.role=="USER"){
              console.log("response recieved")
              console.log(this.customer.role)
              this.router.navigateByUrl("/customer/"+this.customer.emailId,{state :this.customer});
            }
          }
        )
    },
      error: err =>{console.log("Exception occured");
      this.msg="Bad Credentials, please enter valid email and password"
    }
    })
  }
}
