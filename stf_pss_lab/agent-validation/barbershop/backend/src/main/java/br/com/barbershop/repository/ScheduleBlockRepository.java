package br.com.barbershop.repository;
import br.com.barbershop.domain.ScheduleBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
public interface ScheduleBlockRepository extends JpaRepository<ScheduleBlock,Long> { boolean existsByProfessionalIdAndStartAtLessThanAndEndAtGreaterThan(Long id, LocalDateTime end, LocalDateTime start); }
