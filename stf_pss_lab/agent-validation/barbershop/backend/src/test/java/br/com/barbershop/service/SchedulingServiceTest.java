package br.com.barbershop.service;
import br.com.barbershop.api.ApiDtos.*;
import br.com.barbershop.domain.*;
import br.com.barbershop.repository.*;
import org.junit.jupiter.api.*;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.time.*;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class SchedulingServiceTest {
 ProfessionalRepository professionals=mock(ProfessionalRepository.class); BarberServiceRepository services=mock(BarberServiceRepository.class); BookingRepository bookings=mock(BookingRepository.class); ScheduleBlockRepository blocks=mock(ScheduleBlockRepository.class);
 SchedulingService scheduling=new SchedulingService(professionals,services,bookings,blocks); Professional professional=new Professional("Ana"); BarberService service=new BarberService("Corte",30,new BigDecimal("45")); LocalDateTime start=LocalDate.now().plusDays(2).atTime(10,0);
 @BeforeEach void entities(){setId(professional,1L);setId(service,2L);when(professionals.findById(1L)).thenReturn(Optional.of(professional));when(services.findById(2L)).thenReturn(Optional.of(service));}
 @Test void rejectsOverlappingBooking(){when(bookings.existsByProfessionalIdAndStartAtLessThanAndEndAtGreaterThan(1L,start.plusMinutes(30),start)).thenReturn(true);ResponseStatusException ex=assertThrows(ResponseStatusException.class,()->scheduling.book(request()));assertEquals(409,ex.getStatusCode().value());verify(bookings,never()).save(any());}
 @Test void rejectsBlockedTime(){when(blocks.existsByProfessionalIdAndStartAtLessThanAndEndAtGreaterThan(1L,start.plusMinutes(30),start)).thenReturn(true);ResponseStatusException ex=assertThrows(ResponseStatusException.class,()->scheduling.book(request()));assertEquals(409,ex.getStatusCode().value());verify(bookings,never()).save(any());}
 @Test void allowsAdjacentBooking(){when(bookings.save(any())).thenAnswer(i->i.getArgument(0));Booking result=scheduling.book(request());assertEquals(start,result.getStartAt());assertEquals(start.plusMinutes(30),result.getEndAt());}
 @Test void rejectsBlockOverExistingBooking(){LocalDateTime end=start.plusHours(1);when(bookings.existsByProfessionalIdAndStartAtLessThanAndEndAtGreaterThan(1L,end,start)).thenReturn(true);assertEquals(409,assertThrows(ResponseStatusException.class,()->scheduling.block(new BlockRequest(1L,start,end,"Pausa"))).getStatusCode().value());}
 private BookingRequest request(){return new BookingRequest(1L,2L,"Cliente","cliente@email.com",start);}
 private static void setId(Object target,Long id){try{var f=target.getClass().getDeclaredField("id");f.setAccessible(true);f.set(target,id);}catch(Exception e){throw new RuntimeException(e);}}
}
