package com.demo.hibernatehomework;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "profesori")
@Data
public class Profesor {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "nume")
    private String nume;

    @OneToMany(mappedBy = "profesor")
    @JsonIgnore
    private Set<Materie> materiiPredate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profesor profesor = (Profesor) o;
        return id.equals(profesor.id) && nume.equals(profesor.nume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nume);
    }
}
