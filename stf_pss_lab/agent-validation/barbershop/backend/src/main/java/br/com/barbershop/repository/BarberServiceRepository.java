package br.com.barbershop.repository;
import br.com.barbershop.domain.BarberService;
import org.springframework.data.jpa.repository.JpaRepository;
public interface BarberServiceRepository extends JpaRepository<BarberService,Long> {}
