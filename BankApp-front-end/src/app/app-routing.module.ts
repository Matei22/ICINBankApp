import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountsComponent } from './accounts/accounts.component';
import { AdminLoginComponent } from './admin-login/admin-login.component';
import { AdminTemplateComponent } from './admin-template/admin-template.component';
import { CreditAccountsRequestsComponent } from './credit-accounts-requests/credit-accounts-requests.component';
import { CustomerAccountsComponent } from './customer-accounts/customer-accounts.component';
import { CustomerTemplateComponent } from './customer-template/customer-template.component';
import { CustomersComponent } from './customers/customers.component';
import { DebitAccountsRequestsComponent } from './debit-accounts-requests/debit-accounts-requests.component';
import { LoginComponent } from './login/login.component';
import { NewAccountRequestComponent } from './new-account-request/new-account-request.component';
import { NewAccountComponent } from './new-account/new-account.component';
import { NewCustomerComponent } from './new-customer/new-customer.component';
import { RegistrationComponent } from './registration/registration.component';
import { RequestComponent } from './request/request.component';
import { TransferAccountsRequestsComponent } from './transfer-accounts-requests/transfer-accounts-requests.component';

const routes: Routes = [
  { path :"login", component:LoginComponent},
  { path:"registration", component:RegistrationComponent},
  { path :"new-account/:emailId", component:NewAccountComponent},
  { path:"customer/:emailId", component:CustomerTemplateComponent},
  { path:"admin-login", component:AdminLoginComponent},
  { path:"myaccounts", component:CustomerAccountsComponent},
  { path :"admin", component:AdminTemplateComponent, 
  children:[
    { path :"customers", component : CustomersComponent},
    { path :"accounts", component : AccountsComponent},
    { path :"new-customer", component : NewCustomerComponent},
    { path :"customer-accounts/:emailId", component : AccountsComponent},
    { path :"new-account-requests/:emailId", component:NewAccountRequestComponent},
    { path :"transfer-accounts-requests/:emailId", component:TransferAccountsRequestsComponent},
    { path :"debit-accounts-requests/:emailId", component:DebitAccountsRequestsComponent},
    { path :"credit-accounts-requests/:emailId", component:CreditAccountsRequestsComponent},
    { path :"request", component:RequestComponent}
  ]},
  { path :"customer-accounts/:id", component : CustomerAccountsComponent},


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
