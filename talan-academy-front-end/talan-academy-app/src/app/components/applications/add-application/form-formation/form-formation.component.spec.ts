import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormFormationComponent } from './form-formation.component';

describe('FormFormationComponent', () => {
  let component: FormFormationComponent;
  let fixture: ComponentFixture<FormFormationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormFormationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormFormationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
