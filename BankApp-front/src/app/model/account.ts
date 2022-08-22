export class AccountDetails {
    accId:                string;
    balance:              number;
    currentPage:          number;
    totalPages:           number;
    pageSize:             number;
    status:               string;
    accountOperationDTOS: AccountOperationDTOS[];
  }
  
  export interface AccountOperationDTOS {
    operationId:   number;
    operationDate: Date;
    amount:        number;
    type:          string;
    description:   string;
    status:        string;
  }
  