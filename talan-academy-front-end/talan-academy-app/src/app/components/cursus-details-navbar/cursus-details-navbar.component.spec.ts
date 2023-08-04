import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CursusDetailsNavbarComponent } from './cursus-details-navbar.component';

describe('CursusDetailsNavbarComponent', () => {
  let component: CursusDetailsNavbarComponent;
  let fixture: ComponentFixture<CursusDetailsNavbarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CursusDetailsNavbarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CursusDetailsNavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
