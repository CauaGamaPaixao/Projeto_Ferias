package br.com.barbershop.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Booking {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) private Professional professional;
    @ManyToOne(optional=false) private BarberService service;
    @Column(nullable=false, length=100) private String clientName;
    @Column(nullable=false, length=120) private String clientEmail;
    @Column(nullable=false) private LocalDateTime startAt;
    @Column(nullable=false) private LocalDateTime endAt;
    protected Booking() {}
    public Booking(Professional professional, BarberService service, String clientName, String clientEmail, LocalDateTime startAt, LocalDateTime endAt) {
        this.professional=professional; this.service=service; this.clientName=clientName; this.clientEmail=clientEmail; this.startAt=startAt; this.endAt=endAt;
    }
    public Long getId(){return id;} public Professional getProfessional(){return professional;} public BarberService getService(){return service;}
    public String getClientName(){return clientName;} public String getClientEmail(){return clientEmail;} public LocalDateTime getStartAt(){return startAt;} public LocalDateTime getEndAt(){return endAt;}
}

