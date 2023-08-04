import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountValidationModalComponent } from './account-validation-modal.component';

describe('AccountValidationModalComponent', () => {
  let component: AccountValidationModalComponent;
  let fixture: ComponentFixture<AccountValidationModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AccountValidationModalComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AccountValidationModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
