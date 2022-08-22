import { Component, OnInit } from '@angular/core';
import {CustomerService} from "../services/customer.service";


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  loggedIn:boolean
  constructor(private service: CustomerService) { }

  ngOnInit(): void {
    this.loggedIn=this.service.isLogedIn();
  }

}
