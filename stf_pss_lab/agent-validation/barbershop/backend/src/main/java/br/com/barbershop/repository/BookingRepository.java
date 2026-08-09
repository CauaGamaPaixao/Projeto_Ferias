package br.com.barbershop.repository;
import br.com.barbershop.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
public interface BookingRepository extends JpaRepository<Booking,Long> { boolean existsByProfessionalIdAndStartAtLessThanAndEndAtGreaterThan(Long id, LocalDateTime end, LocalDateTime start); }
