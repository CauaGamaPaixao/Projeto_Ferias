import { CommonModule } from '@angular/common';
import { Component,OnInit } from '@angular/core';
import { FormBuilder,ReactiveFormsModule,Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { forkJoin } from 'rxjs';
import { BarbershopApiService } from './barbershop-api.service';
import { BarberService,Professional,Slot } from './models';
@Component({selector:'app-root',standalone:true,imports:[CommonModule,ReactiveFormsModule],templateUrl:'./app.component.html',styleUrl:'./app.component.css'})
export class AppComponent implements OnInit{
 mode:'booking'|'admin'='booking'; professionals:Professional[]=[]; services:BarberService[]=[]; slots:Slot[]=[]; loading=false; searched=false; message=''; error='';
 search=this.fb.nonNullable.group({professionalId:[0,Validators.min(1)],serviceId:[0,Validators.min(1)],date:['',Validators.required]});
 booking=this.fb.nonNullable.group({clientName:['',[Validators.required,Validators.maxLength(100)]],clientEmail:['',[Validators.required,Validators.email]],startAt:['',Validators.required]});
 professional=this.fb.nonNullable.group({name:['',[Validators.required,Validators.maxLength(100)]]});
 service=this.fb.nonNullable.group({name:['',Validators.required],durationMinutes:[30,[Validators.required,Validators.min(15),Validators.max(480)]],price:[45,[Validators.required,Validators.min(.01)]]});
 block=this.fb.nonNullable.group({professionalId:[0,Validators.min(1)],startAt:['',Validators.required],endAt:['',Validators.required],reason:['',[Validators.required,Validators.maxLength(200)]]});
 constructor(private fb:FormBuilder,private api:BarbershopApiService){}
 ngOnInit():void{this.refresh();}
 get minDate():string{return new Date().toISOString().slice(0,10)}
 refresh():void{forkJoin({professionals:this.api.professionals(),services:this.api.services()}).subscribe({next:r=>{this.professionals=r.professionals;this.services=r.services},error:e=>this.fail(e)});}
 findSlots():void{if(this.search.invalid){this.search.markAllAsTouched();return}this.clear();this.loading=true;const v=this.search.getRawValue();this.api.availability(v.professionalId,v.serviceId,v.date).subscribe({next:s=>{this.slots=s;this.searched=true;this.loading=false},error:e=>{this.loading=false;this.fail(e)}});}
 reserve():void{if(this.booking.invalid||!this.booking.value.startAt){this.booking.markAllAsTouched();return}this.clear();const s=this.search.getRawValue(),b=this.booking.getRawValue();this.api.book({professionalId:s.professionalId,serviceId:s.serviceId,clientName:b.clientName,clientEmail:b.clientEmail,startAt:b.startAt}).subscribe({next:()=>{this.message='Reserva confirmada! Enviamos os detalhes para o e-mail informado.';this.booking.reset();this.findSlots()},error:e=>this.fail(e)});}
 addProfessional():void{if(this.professional.invalid){this.professional.markAllAsTouched();return}this.clear();this.api.addProfessional(this.professional.getRawValue().name).subscribe({next:p=>{this.professionals=[...this.professionals,p];this.professional.reset();this.message='Profissional cadastrado.'},error:e=>this.fail(e)});}
 addService():void{if(this.service.invalid){this.service.markAllAsTouched();return}this.clear();this.api.addService(this.service.getRawValue()).subscribe({next:s=>{this.services=[...this.services,s];this.service.reset({name:'',durationMinutes:30,price:45});this.message='Serviço cadastrado.'},error:e=>this.fail(e)});}
 addBlock():void{if(this.block.invalid){this.block.markAllAsTouched();return}const v=this.block.getRawValue();if(v.endAt<=v.startAt){this.error='O fim do bloqueio deve ser posterior ao início.';return}this.clear();this.api.block(v).subscribe({next:()=>{this.block.reset();this.message='Horário bloqueado.'},error:e=>this.fail(e)});}
 choose(slot:Slot):void{this.booking.patchValue({startAt:slot.startAt});this.clear();}
 private clear():void{this.message='';this.error=''}
 private fail(e:unknown):void{const http=e as HttpErrorResponse;this.error=http.error?.message||'Não foi possível concluir. Verifique se a API está em execução.';}
}
