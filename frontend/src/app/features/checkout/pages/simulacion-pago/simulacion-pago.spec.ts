import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SimulacionPago } from './simulacion-pago';

describe('SimulacionPago', () => {
  let component: SimulacionPago;
  let fixture: ComponentFixture<SimulacionPago>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SimulacionPago]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SimulacionPago);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
