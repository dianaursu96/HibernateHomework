package com.demo.hibernatehomework;

import errors.ProfesorNotFound;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.persistence.EntityManager;

import java.util.*;

@RestController
@RequestMapping(path = "/profesor")
public class ProfesorController {
    private final ProfesorRepository profesorRepository;
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    public ProfesorController(ProfesorRepository profesorRepository) {
        this.profesorRepository = profesorRepository;
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody Optional<Profesor> getProfesorId(@PathVariable Integer id) {
        return profesorRepository.findById(id);
    }

    @GetMapping(path = "/materii/{id}")
    public @ResponseBody Set<Materie> getMateriiForProfesor(@PathVariable Integer id) {
        Optional<Profesor> optionalProfesor =  profesorRepository.findById(id);
        if (optionalProfesor.isPresent()) {
            Profesor profesor = optionalProfesor.get();
            return profesor.getMateriiPredate();
        } else {
            throw new ProfesorNotFound("Nu exista profesor cu acest id");
        }
    }

    // controleaza tipul erorii afisate - in cazul asta va aparea 400 Bad Request etc
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProfesorNotFound.class)
    public Map<String, String> handleUserNotFound(ProfesorNotFound err) {
        Map<String, String> profesorErrors = new HashMap<>();
        profesorErrors.put("ProfesorNotFound Error", err.getMessage());
        return profesorErrors;
    }
}
