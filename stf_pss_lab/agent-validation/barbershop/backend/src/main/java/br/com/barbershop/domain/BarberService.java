package br.com.barbershop.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "barber_service")
public class BarberService {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, unique = true, length = 100) private String name;
    @Column(nullable = false) private int durationMinutes;
    @Column(nullable = false, precision = 10, scale = 2) private BigDecimal price;
    protected BarberService() {}
    public BarberService(String name, int durationMinutes, BigDecimal price) { this.name=name; this.durationMinutes=durationMinutes; this.price=price; }
    public Long getId() { return id; }
    public String getName() { return name; }
    public int getDurationMinutes() { return durationMinutes; }
    public BigDecimal getPrice() { return price; }
}

