package br.com.barbershop.config;
import br.com.barbershop.domain.*;
import br.com.barbershop.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import java.math.BigDecimal;
import java.time.*;
@Configuration
public class DemoDataConfig {
 @Bean CommandLineRunner demoData(ProfessionalRepository professionals,BarberServiceRepository services,ScheduleBlockRepository blocks){return args->{
  if(professionals.count()==0){Professional ana=professionals.save(new Professional("Ana Martins"));professionals.save(new Professional("Carlos Lima"));LocalDate next=LocalDate.now().plusDays(1);blocks.save(new ScheduleBlock(ana,next.atTime(12,0),next.atTime(13,0),"Almoço"));}
  if(services.count()==0){services.save(new BarberService("Corte clássico",30,new BigDecimal("45.00")));services.save(new BarberService("Barba",30,new BigDecimal("35.00")));services.save(new BarberService("Corte e barba",60,new BigDecimal("70.00")));}
 };}
}
