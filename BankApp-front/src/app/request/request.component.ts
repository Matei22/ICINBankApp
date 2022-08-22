import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { catchError, map, Observable, throwError } from 'rxjs';
import { Customer } from '../model/customer';
import { CustomerService } from '../services/customer.service';

@Component({
  selector: 'app-request',
  templateUrl: './request.component.html',
  styleUrls: ['./request.component.css']
})
export class RequestComponent implements OnInit {
  customers! : Observable<Array<Customer>>;
  errorMessage!: string;
  searchFormGroup : FormGroup;
  constructor(private customerService : CustomerService, private fb : FormBuilder, private router : Router) { }

  ngOnInit(): void {
    this.searchFormGroup=this.fb.group({
      keyword : this.fb.control("")
    });
    this.handleSearchCustomers();
  }

  handleSearchCustomers() {
    let kw=this.searchFormGroup?.value.keyword;
    this.customers=this.customerService.searchCustomers(kw).pipe(
      catchError(err => {
        this.errorMessage=err.message;
        return throwError(err);
      })
    );
  }

  handleTransferRequests(customer:Customer){
    this.router.navigateByUrl("admin/transfer-accounts-requests/"+customer.emailId,{state :customer});
  }

  handleDebitRequests(customer:Customer){
    this.router.navigateByUrl("admin/debit-accounts-requests/"+customer.emailId,{state :customer});
  }

  handleCreditRequests(customer:Customer){
    this.router.navigateByUrl("admin/credit-accounts-requests/"+customer.emailId,{state :customer});
  }

  handleNewAccountRequests(customer:Customer){
    this.router.navigateByUrl("admin/new-account-requests/"+customer.emailId,{state :customer});
  }
}
