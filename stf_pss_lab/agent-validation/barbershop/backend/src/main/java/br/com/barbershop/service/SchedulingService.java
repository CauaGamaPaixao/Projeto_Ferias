package br.com.barbershop.service;
import br.com.barbershop.api.ApiDtos.*;
import br.com.barbershop.domain.*;
import br.com.barbershop.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.time.*;
import java.util.*;
@Service
public class SchedulingService {
 private static final LocalTime OPEN=LocalTime.of(9,0), CLOSE=LocalTime.of(18,0);
 private final ProfessionalRepository professionals; private final BarberServiceRepository services; private final BookingRepository bookings; private final ScheduleBlockRepository blocks;
 public SchedulingService(ProfessionalRepository p,BarberServiceRepository s,BookingRepository b,ScheduleBlockRepository bl){professionals=p;services=s;bookings=b;blocks=bl;}
 @Transactional public Booking book(BookingRequest r){
  Professional p=findProfessional(r.professionalId()); BarberService s=findService(r.serviceId()); LocalDateTime end=r.startAt().plusMinutes(s.getDurationMinutes()); requireBusinessHours(r.startAt(),end);
  if(bookings.existsByProfessionalIdAndStartAtLessThanAndEndAtGreaterThan(p.getId(),end,r.startAt())) conflict("O profissional já possui uma reserva nesse horário.");
  if(blocks.existsByProfessionalIdAndStartAtLessThanAndEndAtGreaterThan(p.getId(),end,r.startAt())) conflict("O horário está bloqueado para esse profissional.");
  return bookings.save(new Booking(p,s,r.clientName().trim(),r.clientEmail().trim(),r.startAt(),end));
 }
 @Transactional public ScheduleBlock block(BlockRequest r){
  if(!r.endAt().isAfter(r.startAt())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"O fim deve ser posterior ao início.");
  Professional p=findProfessional(r.professionalId());
  if(bookings.existsByProfessionalIdAndStartAtLessThanAndEndAtGreaterThan(p.getId(),r.endAt(),r.startAt())) conflict("Existe uma reserva no período informado.");
  if(blocks.existsByProfessionalIdAndStartAtLessThanAndEndAtGreaterThan(p.getId(),r.endAt(),r.startAt())) conflict("Já existe um bloqueio no período informado.");
  return blocks.save(new ScheduleBlock(p,r.startAt(),r.endAt(),r.reason().trim()));
 }
 @Transactional(readOnly=true) public List<AvailabilitySlot> availability(Long professionalId,Long serviceId,LocalDate date){
  Professional p=findProfessional(professionalId); BarberService s=findService(serviceId); List<AvailabilitySlot> result=new ArrayList<>();
  for(LocalDateTime start=date.atTime(OPEN),close=date.atTime(CLOSE);!start.plusMinutes(s.getDurationMinutes()).isAfter(close);start=start.plusMinutes(30)){
   LocalDateTime end=start.plusMinutes(s.getDurationMinutes());
   if(start.isAfter(LocalDateTime.now())&&!bookings.existsByProfessionalIdAndStartAtLessThanAndEndAtGreaterThan(p.getId(),end,start)&&!blocks.existsByProfessionalIdAndStartAtLessThanAndEndAtGreaterThan(p.getId(),end,start)) result.add(new AvailabilitySlot(start,end));
  } return result;
 }
 public Professional findProfessional(Long id){return professionals.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Profissional não encontrado."));}
 public BarberService findService(Long id){return services.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Serviço não encontrado."));}
 private void requireBusinessHours(LocalDateTime start,LocalDateTime end){if(!start.toLocalDate().equals(end.toLocalDate())||start.toLocalTime().isBefore(OPEN)||end.toLocalTime().isAfter(CLOSE))throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"A reserva deve estar entre 09:00 e 18:00.");}
 private void conflict(String message){throw new ResponseStatusException(HttpStatus.CONFLICT,message);}
}
