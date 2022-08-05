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
  defaultRole = "USER";
  msg ='';
  constructor(private service:RegistrationService, private router:Router, private customerService: CustomerService) { }

  ngOnInit(): void {
  }

  loginUser(){
    this.customer.role = "USER"
    this.service.loginUserFormRemote(this.customer).subscribe({
      next: (data) => {
        console.log("response recieved")
        console.log(this.customer.role)
        this.router.navigateByUrl("/customer/"+this.customer.emailId,{state :this.customer});

    },
      error: err =>{console.log("Exception occured");
      console.log(this.customer.role)
      this.msg="Bad Credentials, please enter valid email and password"
    }
    })
  }
}
