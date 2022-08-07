import {Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {catchError, Observable, throwError} from "rxjs";
import {AccountDetails, AccountOperationDTOS} from "../model/account.model";
import { TransferRequests } from '../model/transfer-requests';
@Injectable({
  providedIn: 'root'
})
export class AccountsService {

  constructor(private http : HttpClient) { }

  public getAccount(accountId : string, page : number, size : number):Observable<AccountDetails>{
    return this.http.get<AccountDetails>(environment.backendHost+"/accounts/"+accountId+"/pageOperations?page="+page+"&size="+size)
  }

  public getAccountsFromCustomer(email:String ,accountId : string, page : number, size : number):Observable<AccountDetails>{
    return this.http.get<AccountDetails>(environment.backendHost+"/customer/"+email+"/accounts/"+accountId+"/pageOperations?page="+page+"&size="+size).pipe(
      catchError(this.handleError)
    );
  }

  public getAccounts(customerId : number):Observable<Array<AccountDetails>>{
    return this.http.get<Array<AccountDetails>>(environment.backendHost+"/accounts/"+customerId);
  }


  public getCreditPendingAccounts(customerId : string):Observable<Array<AccountOperationDTOS>>{
  return this.http.get<Array<AccountOperationDTOS>>(environment.backendHost+"/accounts/"+customerId + "/credit");
  }

  public getDebitPendingAccounts(customerId : string):Observable<Array<AccountOperationDTOS>>{
    return this.http.get<Array<AccountOperationDTOS>>(environment.backendHost+"/accounts/"+customerId + "/debit");
  }

  public getTransactionsBasedOnCustomer(customerId :string):Observable<Array<TransferRequests>>{
    return this.http.get<Array<TransferRequests>>(environment.backendHost + "/accounts/transfer/" + customerId)
  }

  public debit(status: string, accountId : string, amount : number, description:string){
    let data={accountId : accountId, amount : amount, description : description, status:status}
    return this.http.post(environment.backendHost+"/accounts/debit",data);
  }
  public credit(status: string,accountId : string, amount : number, description:string){
    let data={accountId : accountId, amount : amount, description : description,status: status}
    return this.http.post(environment.backendHost+"/accounts/credit",data);
  }
  public transfer(status: string,accountSource: string, accountDestination: string, amount : number, description:string):Observable<TransferRequests>{
    let data={accountSource:accountSource, accountDestination:accountDestination, amount:amount, description:description,status: status }
    return this.http.post<TransferRequests>(environment.backendHost+"/accounts/transfer",data);
  }
  public updateOperationStatusToAcceptedCred(operationId:number):Observable<AccountOperationDTOS>{
    let data = {}
    return this.http.post<AccountOperationDTOS>(environment.backendHost+"/accounts/"+operationId+"/credit/accepted",data);
  }
  public updateOperationStatusToDeniedCred(operationId:number):Observable<AccountOperationDTOS >{
    let data={}
    return this.http.post<AccountOperationDTOS>(environment.backendHost+"/accounts/"+operationId+"/credit/denied",data);
  }
  public updateOperationStatusToAcceptedDeb(operationId:number):Observable<AccountOperationDTOS>{
    let data = {}
    return this.http.post<AccountOperationDTOS>(environment.backendHost+"/accounts/"+operationId+"/debit/accepted",data);
  }
  public updateOperationStatusToDeniedDeb(operationId:number):Observable<AccountOperationDTOS >{
    let data={}
    return this.http.post<AccountOperationDTOS>(environment.backendHost+"/accounts/"+operationId+"/debit/denied",data);
  }
  public updateTransactionStatusToDenied(transactionId:number):Observable<TransferRequests >{
    let data={}
    return this.http.post<TransferRequests>(environment.backendHost+"/accounts/"+transactionId+"/request/denied",data);
  }
  public updateTransactionStatusToAccepted(transactionId:number):Observable<TransferRequests >{
    let data={}
    return this.http.post<TransferRequests>(environment.backendHost+"/accounts/"+transactionId+"/request/accepted",data);
  }
  public newAccount(bankAccountId:string, balance:number, type:string, emailId:string):Observable<AccountDetails>{
    let data = {bankAccountId:bankAccountId, balance:balance, type:type, emailId:emailId}
    return this.http.post<AccountDetails>(environment.backendHost + "/accounts/new/" + bankAccountId+"/" + 
    balance + "/" + type + "/" + emailId, data);
  }
  public getAccountsByStatus(customerId : string, status : string):Observable<AccountDetails[]>{
    return this.http.get<AccountDetails[]>(environment.backendHost+"/accounts/"+customerId +"/" +status);
  }
  public updateAccountStatus(emailId:string, accountId:string, status:string):Observable<AccountDetails>{
    let data = {emailId:emailId, accountId:accountId, status:status}
    return this.http.post<AccountDetails>(environment.backendHost+"/accounts/"+emailId +"/" +accountId +"/" + status, data)
  }
  private handleError(error: HttpErrorResponse) {
    let errorMessage = ''
    if (error.status === 0) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.error(
        `Backend returned code ${error.status}, body was: `, error.error);
        errorMessage = `Backend returned code ${error.status}, body was: `, error.error
    }
    // Return an observable with a user-facing error message.
    errorMessage+='Something bad happened,';
    return throwError(() => new Error(errorMessage + "please choose a proper id"));
  }
}
