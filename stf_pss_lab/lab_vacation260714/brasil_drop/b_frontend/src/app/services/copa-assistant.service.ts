import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class CopaAssistantService {
  constructor(private http: HttpClient) {}

  ask(message: string): Observable<{ response: string }> {
    return this.http.post<{ response: string }>('/api/copa-assistant/chat', { message });
  }
}
