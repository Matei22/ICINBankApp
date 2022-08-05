import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DebitAccountsRequestsComponent } from './debit-accounts-requests.component';

describe('DebitAccountsRequestsComponent', () => {
  let component: DebitAccountsRequestsComponent;
  let fixture: ComponentFixture<DebitAccountsRequestsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DebitAccountsRequestsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DebitAccountsRequestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
