package br.com.barbershop.api;
import br.com.barbershop.api.ApiDtos.*;
import br.com.barbershop.domain.*;
import br.com.barbershop.repository.*;
import br.com.barbershop.service.SchedulingService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
@RestController @RequestMapping("/api")
public class BarbershopController {
 private final ProfessionalRepository professionals; private final BarberServiceRepository services; private final SchedulingService scheduling;
 public BarbershopController(ProfessionalRepository p,BarberServiceRepository s,SchedulingService sc){professionals=p;services=s;scheduling=sc;}
 @GetMapping("/professionals") public List<Professional> professionals(){return professionals.findAll();}
 @PostMapping("/professionals") @ResponseStatus(HttpStatus.CREATED) public Professional professional(@Valid @RequestBody NameRequest r){return professionals.save(new Professional(r.name().trim()));}
 @GetMapping("/services") public List<BarberService> services(){return services.findAll();}
 @PostMapping("/services") @ResponseStatus(HttpStatus.CREATED) public BarberService service(@Valid @RequestBody ServiceRequest r){return services.save(new BarberService(r.name().trim(),r.durationMinutes(),r.price()));}
 @GetMapping("/availability") public List<AvailabilitySlot> availability(@RequestParam Long professionalId,@RequestParam Long serviceId,@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate date){return scheduling.availability(professionalId,serviceId,date);}
 @PostMapping("/bookings") @ResponseStatus(HttpStatus.CREATED) public Booking booking(@Valid @RequestBody BookingRequest r){return scheduling.book(r);}
 @PostMapping("/blocks") @ResponseStatus(HttpStatus.CREATED) public ScheduleBlock block(@Valid @RequestBody BlockRequest r){return scheduling.block(r);}
}
