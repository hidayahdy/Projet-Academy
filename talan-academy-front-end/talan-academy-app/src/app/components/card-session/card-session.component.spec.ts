import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardSessionComponent } from './card-session.component';

describe('CardSessionComponent', () => {
  let component: CardSessionComponent;
  let fixture: ComponentFixture<CardSessionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CardSessionComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CardSessionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
