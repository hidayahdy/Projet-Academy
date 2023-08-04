import { TestBed } from '@angular/core/testing';

import { SessionServiceService } from './session-service.service';

describe('SessionServiceService', () => {
  let service: SessionServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SessionServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
