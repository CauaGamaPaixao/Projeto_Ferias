import { Injectable } from '@angular/core';
import { HttpClient,HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BarberService,BlockRequest,BookingRequest,Professional,Slot } from './models';
@Injectable({providedIn:'root'}) export class BarbershopApiService{
 private readonly base='/api';constructor(private http:HttpClient){}
 professionals():Observable<Professional[]>{return this.http.get<Professional[]>(`${this.base}/professionals`)}
 services():Observable<BarberService[]>{return this.http.get<BarberService[]>(`${this.base}/services`)}
 availability(professionalId:number,serviceId:number,date:string):Observable<Slot[]>{return this.http.get<Slot[]>(`${this.base}/availability`,{params:new HttpParams().set('professionalId',professionalId).set('serviceId',serviceId).set('date',date)})}
 book(body:BookingRequest):Observable<unknown>{return this.http.post(`${this.base}/bookings`,body)}
 addProfessional(name:string):Observable<Professional>{return this.http.post<Professional>(`${this.base}/professionals`,{name})}
 addService(body:{name:string;durationMinutes:number;price:number}):Observable<BarberService>{return this.http.post<BarberService>(`${this.base}/services`,body)}
 block(body:BlockRequest):Observable<unknown>{return this.http.post(`${this.base}/blocks`,body)}
}
