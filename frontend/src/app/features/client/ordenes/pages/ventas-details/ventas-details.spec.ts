import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VentasDetails } from './ventas-details';

describe('VentasDetails', () => {
  let component: VentasDetails;
  let fixture: ComponentFixture<VentasDetails>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VentasDetails]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VentasDetails);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
