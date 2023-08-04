import { TestBed } from '@angular/core/testing';

import { AddCursusService } from './add-cursus.service';

describe('AddCursusService', () => {
  let service: AddCursusService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AddCursusService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
