import { TestBed } from '@angular/core/testing';

import { OrdenDetails } from './orden.details';

describe('OrdenDetails', () => {
  let service: OrdenDetails;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrdenDetails);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
