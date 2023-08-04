import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SessionAdminPageComponent } from './session-admin-page.component';

describe('SessionAdminPageComponent', () => {
  let component: SessionAdminPageComponent;
  let fixture: ComponentFixture<SessionAdminPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SessionAdminPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SessionAdminPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
