export interface Professional{id:number;name:string}
export interface BarberService{id:number;name:string;durationMinutes:number;price:number}
export interface Slot{startAt:string;endAt:string}
export interface BookingRequest{professionalId:number;serviceId:number;clientName:string;clientEmail:string;startAt:string}
export interface BlockRequest{professionalId:number;startAt:string;endAt:string;reason:string}
