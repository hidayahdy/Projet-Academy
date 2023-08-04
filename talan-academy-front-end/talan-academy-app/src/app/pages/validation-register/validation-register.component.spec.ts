import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ValidationRegisterComponent } from './validation-register.component';

describe('ValidationRegisterComponent', () => {
  let component: ValidationRegisterComponent;
  let fixture: ComponentFixture<ValidationRegisterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ValidationRegisterComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ValidationRegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
