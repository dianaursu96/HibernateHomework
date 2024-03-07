package com.demo.hibernatehomework;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByNume(String nume);

    Optional<Student> findByNumeAndPrenume(String nume, String prenume);
    @Query("SELECT s FROM Student s WHERE s.nume = ?1")
    List<Student> findByNumeUsingQuery(String nume);


    @Query(value = "SELECT s.* FROM studenti s " +
            "JOIN adrese_studenti a ON s.id_adresa = a.id " +
            "WHERE " +
            "s.nume = ?1 " +
            "AND a.localitate = ?2",
            nativeQuery = true)
    List<Optional<Student>> findByNumeAndLocalitate(String nume, String localitate);

    @Query(value = "SELECT s.* FROM studenti s " +
            "JOIN adrese_studenti a ON s.id_adresa = a.id " +
            "WHERE " +
            "s.cnp = ?1 ",
            nativeQuery = true)
    List<Optional<Student>> findByCNP(String cnp);
}
