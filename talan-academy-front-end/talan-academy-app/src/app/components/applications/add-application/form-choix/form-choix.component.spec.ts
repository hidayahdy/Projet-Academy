import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormChoixComponent } from './form-choix.component';

describe('FormChoixComponent', () => {
  let component: FormChoixComponent;
  let fixture: ComponentFixture<FormChoixComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormChoixComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormChoixComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
