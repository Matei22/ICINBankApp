import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountsComponent } from './accounts/accounts.component';
import { WelcomeComponent } from './components/welcome/welcome.component';
import { CustomersComponent } from './customers/customers.component';
import { NewCustomerComponent } from './new-customer/new-customer.component';

const routes: Routes = [
  {path:'', redirectTo:"",pathMatch:"full"},
  {path:"welcome", component:WelcomeComponent},
  {path:"accounts", component:AccountsComponent},
  {path:"customers", component:CustomersComponent},
  {path:"new-customer", component:NewCustomerComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
