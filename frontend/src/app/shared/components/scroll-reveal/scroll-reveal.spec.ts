import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ScrollReveal } from './scroll-reveal';

describe('ScrollReveal', () => {
  let component: ScrollReveal;
  let fixture: ComponentFixture<ScrollReveal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ScrollReveal]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ScrollReveal);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
