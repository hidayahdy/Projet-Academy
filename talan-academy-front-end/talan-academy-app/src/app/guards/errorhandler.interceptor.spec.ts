import { TestBed } from '@angular/core/testing';

import { ErrorhandlerInterceptor } from './errorhandler.interceptor';

describe('ErrorhandlerInterceptor', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      ErrorhandlerInterceptor
      ]
  }));

  it('should be created', () => {
    const interceptor: ErrorhandlerInterceptor = TestBed.inject(ErrorhandlerInterceptor);
    expect(interceptor).toBeTruthy();
  });
});
