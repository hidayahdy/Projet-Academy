import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardCursusComponent } from './card-cursus.component';

describe('CardCursusComponent', () => {
  let component: CardCursusComponent;
  let fixture: ComponentFixture<CardCursusComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CardCursusComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CardCursusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
