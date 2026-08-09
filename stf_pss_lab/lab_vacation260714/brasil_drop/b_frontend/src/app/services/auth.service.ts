import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';

interface User { name: string; email: string; }

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly baseUrl = '/api/auth';
  private userSubject = new BehaviorSubject<User | null>(null);
  user$ = this.userSubject.asObservable();

  constructor(private http: HttpClient) {
    this.http.get<User | null>(`${this.baseUrl}/me`).subscribe({
      next: user => this.userSubject.next(user),
      error: () => this.userSubject.next(null)
    });
  }

  login(email: string, password: string): Observable<User> {
    return this.http.post<User>(`${this.baseUrl}/login`, { email, password }).pipe(
      tap(user => this.userSubject.next(user))
    );
  }

  register(name: string, email: string, password: string): Observable<User> {
    return this.http.post<User>(`${this.baseUrl}/register`, { name, email, password }).pipe(
      tap(user => this.userSubject.next(user))
    );
  }

  logout(): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/logout`, {}).pipe(
      tap(() => this.userSubject.next(null))
    );
  }

  get currentUser(): User | null { return this.userSubject.value; }
  get isLoggedIn(): boolean { return this.userSubject.value !== null; }
}
