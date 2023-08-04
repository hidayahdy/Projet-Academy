import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardRegistredComponent } from './dashboard-registred.component';

describe('DashboardRegistredComponent', () => {
  let component: DashboardRegistredComponent;
  let fixture: ComponentFixture<DashboardRegistredComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DashboardRegistredComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardRegistredComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
