import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddCursusFormComponent } from './add-cursus-form.component';

describe('AddCursusFormComponent', () => {
  let component: AddCursusFormComponent;
  let fixture: ComponentFixture<AddCursusFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddCursusFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddCursusFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
