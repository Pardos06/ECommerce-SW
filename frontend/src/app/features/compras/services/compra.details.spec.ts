import { TestBed } from '@angular/core/testing';

import { CompraDetails } from './compra.details';

describe('CompraDetails', () => {
  let service: CompraDetails;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CompraDetails);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
