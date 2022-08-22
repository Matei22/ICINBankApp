import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Customer } from '../model/customer';
import { CustomerService } from '../services/customer.service';
import { RegistrationService } from '../services/registration.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  customer = new Customer();
  loggedInCustomer : Customer;
  msg ='';
  constructor(private service:RegistrationService, private router:Router, private customerService: CustomerService) { }

  ngOnInit(): void {
    localStorage.clear()
  }

  loginUser(){

    this.service.loginUserFormRemote(this.customer).subscribe({
      next: () => {
        this.customerService.getCustomer(this.customer.emailId).subscribe(
          (data)=>{
            this.loggedInCustomer = data
            localStorage.setItem('emailId',this.loggedInCustomer.emailId)
            if(this.loggedInCustomer.role=="ADMIN"){
              this.router.navigateByUrl("/admin/home");
            }else if(this.loggedInCustomer.role=="USER"){
              console.log("response recieved")
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
  RedirectRegister(){
    this.router.navigate(['access/register']);
  }
}
