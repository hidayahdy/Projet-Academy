import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddSessionFormComponent } from './add-session-form.component';

describe('AddSessionFormComponent', () => {
  let component: AddSessionFormComponent;
  let fixture: ComponentFixture<AddSessionFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddSessionFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddSessionFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
