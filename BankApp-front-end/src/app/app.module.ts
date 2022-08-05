import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { CustomersComponent } from './customers/customers.component';
import { AccountsComponent } from './accounts/accounts.component';
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { NewCustomerComponent } from './new-customer/new-customer.component';
import { CustomerAccountsComponent } from './customer-accounts/customer-accounts.component';
import { LoginComponent } from './login/login.component';
import { AdminTemplateComponent } from './admin-template/admin-template.component';
import { RegistrationComponent } from './registration/registration.component';
import { CustomerTemplateComponent } from './customer-template/customer-template.component';
import { AdminLoginComponent } from './admin-login/admin-login.component';
import { NewAccountComponent } from './new-account/new-account.component';
import { RequestComponent } from './request/request.component';
import { NewAccountRequestComponent } from './new-account-request/new-account-request.component';
import { CreditAccountsRequestsComponent } from './credit-accounts-requests/credit-accounts-requests.component';
import { DebitAccountsRequestsComponent } from './debit-accounts-requests/debit-accounts-requests.component';
import { TransferAccountsRequestsComponent } from './transfer-accounts-requests/transfer-accounts-requests.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    CustomersComponent,
    AccountsComponent,
    NewCustomerComponent,
    CustomerAccountsComponent,
    LoginComponent,
    AdminTemplateComponent,
    RegistrationComponent,
    CustomerTemplateComponent,
    AdminLoginComponent,
    NewAccountComponent,
    RequestComponent,
    NewAccountRequestComponent,
    CreditAccountsRequestsComponent,
    DebitAccountsRequestsComponent,
    TransferAccountsRequestsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
