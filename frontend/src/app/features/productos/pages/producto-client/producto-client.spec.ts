import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductoClient } from './producto-client';

describe('ProductoClient', () => {
  let component: ProductoClient;
  let fixture: ComponentFixture<ProductoClient>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductoClient]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductoClient);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
