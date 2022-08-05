import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Customer } from './model/customer.model';
import{HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  constructor(private http:HttpClient) { }

  public loginUserFormRemote(customer:Customer):Observable<any>{
    return this.http.post<any>("http://localhost:8090/login", customer)
  }

  public registerUserFormRemote(customer:Customer):Observable<any>{
    return this.http.post<any>("http://localhost:8090/register", customer)
  }

  handleError(error:Response){

  }
}
