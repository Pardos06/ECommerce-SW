import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TarjetaPago } from './tarjeta-pago';

describe('TarjetaPago', () => {
  let component: TarjetaPago;
  let fixture: ComponentFixture<TarjetaPago>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TarjetaPago]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TarjetaPago);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
