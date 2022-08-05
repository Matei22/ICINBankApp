import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Customer } from '../model/customer.model';
import { RegistrationService } from '../registration.service';

@Component({
  selector: 'app-admin-login',
  templateUrl: './admin-login.component.html',
  styleUrls: ['./admin-login.component.css']
})
export class AdminLoginComponent implements OnInit {

  customer = new Customer();
  msg ='';
  defaultRole="ADMIN";
  constructor(private service:RegistrationService, private route:Router) { }

  ngOnInit(): void {
  }

  loginUser(){
    this.customer.role = "ADMIN"
    this.service.loginUserFormRemote(this.customer).subscribe({
      next: (data) => {
        console.log("response recieved")
        this.route.navigateByUrl('/admin')
    },
      error: err =>{console.log("Exception occured");
      this.msg="Bad Credentials, please enter valid email and password"
    }
    })
  }

}
