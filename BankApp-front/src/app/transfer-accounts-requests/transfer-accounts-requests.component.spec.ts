import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransferAccountsRequestsComponent } from './transfer-accounts-requests.component';

describe('TransferAccountsRequestsComponent', () => {
  let component: TransferAccountsRequestsComponent;
  let fixture: ComponentFixture<TransferAccountsRequestsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TransferAccountsRequestsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransferAccountsRequestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
