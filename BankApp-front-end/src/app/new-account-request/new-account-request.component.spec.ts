import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewAccountRequestComponent } from './new-account-request.component';

describe('NewAccountRequestComponent', () => {
  let component: NewAccountRequestComponent;
  let fixture: ComponentFixture<NewAccountRequestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewAccountRequestComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewAccountRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
