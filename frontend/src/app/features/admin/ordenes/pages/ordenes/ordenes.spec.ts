import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrdenesAdminPage } from './ordenes';

describe('OrdenesAdminPage', () => {
  let component: OrdenesAdminPage;
  let fixture: ComponentFixture<OrdenesAdminPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OrdenesAdminPage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrdenesAdminPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
