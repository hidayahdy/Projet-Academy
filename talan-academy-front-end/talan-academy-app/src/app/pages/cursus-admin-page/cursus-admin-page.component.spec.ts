import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CursusAdminPageComponent } from './cursus-admin-page.component';

describe('CursusAdminPageComponent', () => {
  let component: CursusAdminPageComponent;
  let fixture: ComponentFixture<CursusAdminPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CursusAdminPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CursusAdminPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
