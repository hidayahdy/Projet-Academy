import { TestBed } from '@angular/core/testing';

import { CursusPublicService } from './cursus-public.service';

describe('CursusPublicServiceService', () => {
  let service: CursusPublicService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CursusPublicService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
