import { TestBed } from '@angular/core/testing';

import { CopaAssistantService } from './copa-assistant.service';

describe('CopaAssistantService', () => {
  let service: CopaAssistantService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CopaAssistantService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
