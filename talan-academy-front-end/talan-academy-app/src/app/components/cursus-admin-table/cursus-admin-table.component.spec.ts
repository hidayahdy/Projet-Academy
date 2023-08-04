import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CursusAdminTableComponent } from './cursus-admin-table.component';

describe('CursusAdminTableComponent', () => {
  let component: CursusAdminTableComponent;
  let fixture: ComponentFixture<CursusAdminTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CursusAdminTableComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CursusAdminTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
