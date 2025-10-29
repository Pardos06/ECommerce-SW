import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompraDetails } from './compra-details';

describe('CompraDetails', () => {
  let component: CompraDetails;
  let fixture: ComponentFixture<CompraDetails>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CompraDetails]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CompraDetails);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
