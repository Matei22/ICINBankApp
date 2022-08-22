import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Customer } from '../model/customer';
import { RegistrationService } from '../services/registration.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
  user = new Customer();
  msg ='';
  constructor(private service: RegistrationService, private router: Router) { }

  ngOnInit(): void {
  }
  registerUser(){
    this.user.role = "USER";
    this.service.registerUserFormRemote(this.user).subscribe({
      next: (data) => {
        console.log("response recieved")
        this.router.navigateByUrl('/login')
    },
      error: err =>{console.log("Exception occured");
      this.msg="User with email: " + this.user.emailId + " is already existing";

    }
    })
  }

}
