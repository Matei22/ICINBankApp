import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreditAccountsRequestsComponent } from './credit-accounts-requests.component';

describe('CreditAccountsRequestsComponent', () => {
  let component: CreditAccountsRequestsComponent;
  let fixture: ComponentFixture<CreditAccountsRequestsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreditAccountsRequestsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreditAccountsRequestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
