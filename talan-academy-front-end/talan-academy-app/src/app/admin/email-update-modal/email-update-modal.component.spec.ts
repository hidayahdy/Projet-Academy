import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmailUpdateModalComponent } from './email-update-modal.component';

describe('EmailUpdateModalComponent', () => {
  let component: EmailUpdateModalComponent;
  let fixture: ComponentFixture<EmailUpdateModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EmailUpdateModalComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EmailUpdateModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
