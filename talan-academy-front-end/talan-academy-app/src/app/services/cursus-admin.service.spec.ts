import { TestBed } from '@angular/core/testing';

import { CursusAdminService } from './cursus-admin.service';

describe('CursusAdminService', () => {
  let service: CursusAdminService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CursusAdminService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
