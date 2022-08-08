import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Customer } from '../model/customer.model';
import { RegistrationService } from '../registration.service';
import { Router } from '@angular/router';
import { CustomerService } from '../services/customer.service';

@Component({
  standalone: true,
  selector: 'app-login',
  imports: [CommonModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  customer = new Customer();
  loggedInCustomer : Customer;
  msg ='';
  constructor(private service:RegistrationService, private router:Router, private customerService: CustomerService) { }

  ngOnInit(): void {
    localStorage.clear()
  }

  loginUser(loginData:any){
    console.log(loginData.value)

    this.service.loginUserFormRemote(this.customer).subscribe({
      next: () => {
        this.customerService.getCustomer(this.customer.emailId).subscribe(
          (data)=>{
            this.loggedInCustomer = data;
            localStorage.setItem('emailId', this.loggedInCustomer.emailId)
            if(this.loggedInCustomer.role=="ADMIN"){
              this.router.navigateByUrl("/admin");
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
    console.log(loginData.value)
  }

}
