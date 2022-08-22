import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AccountsComponent } from './accounts/accounts.component';
import { AdminTemplateComponent } from './admin-template/admin-template.component';
import { CreditAccountsRequestsComponent } from './credit-accounts-requests/credit-accounts-requests.component';
import { CustomerAccountsComponent } from './customer-accounts/customer-accounts.component';
import { CustomerTemplateComponent } from './customer-template/customer-template.component';
import { DebitAccountsRequestsComponent } from './debit-accounts-requests/debit-accounts-requests.component';
import { ErrorUrlComponent } from './error-url/error-url.component';
import { HomeComponent } from './home/home.component';
import { HttpClientModule} from "@angular/common/http";
import { NavbarComponent } from './navbar/navbar.component';
import { NewAccountComponent } from './new-account/new-account.component';
import { NewAccountRequestComponent } from './new-account-request/new-account-request.component';
import { NewCustomerComponent } from './new-customer/new-customer.component';
import { RegistrationComponent } from './registration/registration.component';
import { RequestComponent } from './request/request.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CustomersComponent } from './customers/customers.component';
import { TransferAccountsRequestsComponent } from './transfer-accounts-requests/transfer-accounts-requests.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginPageComponent } from './login-page/login-page.component';

@NgModule({
  declarations: [
    AppComponent,
    AccountsComponent,
    AdminTemplateComponent,
    CreditAccountsRequestsComponent,
    CustomerAccountsComponent,
    CustomerTemplateComponent,
    DebitAccountsRequestsComponent,
    ErrorUrlComponent,
    HomeComponent,
    NavbarComponent,
    NewAccountComponent,
    NewAccountRequestComponent,
    NewCustomerComponent,
    RegistrationComponent,
    RequestComponent,
    CustomersComponent,
    TransferAccountsRequestsComponent,
    LoginPageComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
