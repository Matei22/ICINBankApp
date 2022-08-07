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

  constructor() {}

  ngOnInit(): void {
    
  }
}
