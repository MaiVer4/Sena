import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Display } from './display';

describe('Display', () => {
  let component: Display;
  let fixture: ComponentFixture<Display>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Display],
    }).compileComponents();

    fixture = TestBed.createComponent(Display);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
