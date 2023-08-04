import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SessionAdminTableComponent } from './session-admin-table.component';

describe('SessionAdminTableComponent', () => {
  let component: SessionAdminTableComponent;
  let fixture: ComponentFixture<SessionAdminTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SessionAdminTableComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SessionAdminTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
