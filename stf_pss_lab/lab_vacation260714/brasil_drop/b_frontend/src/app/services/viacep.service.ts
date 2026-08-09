import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ViaCepAddress } from '../models/product.model';

@Injectable({ providedIn: 'root' })
export class ViacepService {
  constructor(private http: HttpClient) {}

  lookup(cep: string): Observable<ViaCepAddress> {
    const clean = cep.replace(/\D/g, '');
    return this.http.get<ViaCepAddress>(`https://viacep.com.br/ws/${clean}/json/`);
  }
}
