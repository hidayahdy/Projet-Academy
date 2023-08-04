import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplicationsAdminPageComponent } from './applications-admin-page.component';

describe('ApplicationsAdminPageComponent', () => {
  let component: ApplicationsAdminPageComponent;
  let fixture: ComponentFixture<ApplicationsAdminPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ApplicationsAdminPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ApplicationsAdminPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
