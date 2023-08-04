import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SectionCursusHomeComponent } from './section-cursus-home.component';

describe('SectionCursusHomeComponent', () => {
  let component: SectionCursusHomeComponent;
  let fixture: ComponentFixture<SectionCursusHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SectionCursusHomeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SectionCursusHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
