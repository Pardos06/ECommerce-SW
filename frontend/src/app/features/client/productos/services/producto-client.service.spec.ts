import { TestBed } from '@angular/core/testing';

import { ProductoClientService } from './producto-client.service';

describe('ProductoClientService', () => {
  let service: ProductoClientService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProductoClientService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
