package br.com.barbershop.domain;

import jakarta.persistence.*;

@Entity
public class Professional {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, unique = true, length = 100) private String name;
    protected Professional() {}
    public Professional(String name) { this.name = name; }
    public Long getId() { return id; }
    public String getName() { return name; }
}

