import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormIdentityComponent } from './form-identity.component';

describe('FormIdentityComponent', () => {
  let component: FormIdentityComponent;
  let fixture: ComponentFixture<FormIdentityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormIdentityComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormIdentityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
