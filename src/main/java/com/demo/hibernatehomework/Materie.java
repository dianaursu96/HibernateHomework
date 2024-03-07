package com.demo.hibernatehomework;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "materii")
@Data
public class Materie {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "nume")
    private String nume;

    @ManyToOne
    @JoinColumn(name = "id_profesor")
    private Profesor profesor;

    @ManyToMany(mappedBy = "cursuriAlese", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Student> studentiInrolati;
}
