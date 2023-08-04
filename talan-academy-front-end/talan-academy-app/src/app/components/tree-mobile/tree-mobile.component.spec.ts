import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TreeMobileComponent } from './tree-mobile.component';

describe('TreeMobileComponent', () => {
  let component: TreeMobileComponent;
  let fixture: ComponentFixture<TreeMobileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TreeMobileComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TreeMobileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
