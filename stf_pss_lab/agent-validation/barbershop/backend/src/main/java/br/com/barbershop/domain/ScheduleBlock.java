package br.com.barbershop.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="schedule_block")
public class ScheduleBlock {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) private Professional professional;
    @Column(nullable=false) private LocalDateTime startAt;
    @Column(nullable=false) private LocalDateTime endAt;
    @Column(nullable=false, length=200) private String reason;
    protected ScheduleBlock() {}
    public ScheduleBlock(Professional professional, LocalDateTime startAt, LocalDateTime endAt, String reason){this.professional=professional;this.startAt=startAt;this.endAt=endAt;this.reason=reason;}
    public Long getId(){return id;} public Professional getProfessional(){return professional;} public LocalDateTime getStartAt(){return startAt;} public LocalDateTime getEndAt(){return endAt;} public String getReason(){return reason;}
}

