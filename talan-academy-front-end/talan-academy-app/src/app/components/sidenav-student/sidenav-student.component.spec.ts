import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SidenavStudentComponent } from './sidenav-student.component';

describe('SidenavStudentComponent', () => {
  let component: SidenavStudentComponent;
  let fixture: ComponentFixture<SidenavStudentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SidenavStudentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SidenavStudentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
