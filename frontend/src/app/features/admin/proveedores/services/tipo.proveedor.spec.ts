import { TestBed } from '@angular/core/testing';

import { TipoProveedor } from './tipo.proveedor';

describe('TipoProveedor', () => {
  let service: TipoProveedor;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TipoProveedor);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
