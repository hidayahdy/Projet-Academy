import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CursusDetailsComponent } from './cursus-details.component';

describe('CursusDetailsComponent', () => {
  let component: CursusDetailsComponent;
  let fixture: ComponentFixture<CursusDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CursusDetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CursusDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
