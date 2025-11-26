import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CargoPage } from './cargo';

describe('Cargo', () => {
  let component: CargoPage;
  let fixture: ComponentFixture<CargoPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CargoPage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CargoPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
