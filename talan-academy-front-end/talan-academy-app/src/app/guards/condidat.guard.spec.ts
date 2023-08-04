import { TestBed } from '@angular/core/testing';

import { CondidatGuard } from './condidat.guard';

describe('AuthGuard', () => {
  let guard: CondidatGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(CondidatGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
