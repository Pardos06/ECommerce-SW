import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TipoProveedor } from './tipo-proveedor';

describe('TipoProveedor', () => {
  let component: TipoProveedor;
  let fixture: ComponentFixture<TipoProveedor>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TipoProveedor]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TipoProveedor);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
