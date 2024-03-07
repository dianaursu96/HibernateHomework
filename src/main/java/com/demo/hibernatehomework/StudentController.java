package com.demo.hibernatehomework;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/student")
public class StudentController {
    StudentRepository studentRepository;
    MaterieRepository materieRepository;
    AdresaRepository adresaRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository,
                             MaterieRepository materieRepository,
                             AdresaRepository adresaRepository) {
        this.studentRepository = studentRepository;
        this.materieRepository = materieRepository;
        this.adresaRepository = adresaRepository;
    }
    @PostMapping(path = "/enroll")
    public ResponseEntity<String> enrollStudentToMaterie(@RequestParam(name = "studentId") Integer studentId,
                                                         @RequestParam(name = "materieId") Integer materieId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        Optional<Materie> optionalMaterie = materieRepository.findById(materieId);

        if (optionalStudent.isPresent() && optionalMaterie.isPresent()) {
            Student student = optionalStudent.get();
            Materie materie = optionalMaterie.get();

            // Add materie to the student's set of cursuriAlese
            student.getCursuriAlese().add(materie);
            materie.getStudentiInrolati().add(student);


            // Update the student
            studentRepository.save(student);
            materieRepository.save(materie);

            return ResponseEntity.ok("Studentul a fost inscris la materie.");
        } else {
            return ResponseEntity.badRequest().body("Studentul sau materia nu au fost gasite.");
        }
    }

    @PostMapping
    public ResponseEntity<String> createStudent(@RequestBody StudentRequest studentRequest) {
        AdresaRequest adresaRequest = studentRequest.getAdresa();
        Adresa adresa = new Adresa();
        adresa.setStrada(adresaRequest.getStrada());
        adresa.setNumar(adresaRequest.getNumar());
        adresa.setLocalitate(adresaRequest.getLocalitate());
        adresaRepository.save(adresa); // Save the Adresa first

        Student student = new Student();
        student.setNume(studentRequest.getNume());
        student.setPrenume(studentRequest.getPrenume());
        student.setCnp(studentRequest.getCnp());
        student.setAdresa(adresa);

        studentRepository.save(student);

        return ResponseEntity.ok("Studentul a fost creat.");

    }

    @PutMapping(path = "/{id}/adresa")
    public ResponseEntity<String> updateStudent(@PathVariable Integer id,
                                                @RequestBody AdresaRequest adresaRequest) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            // obtine adresa studentului si fa update la campuri
            Adresa adresa = student.getAdresa();
            adresa.setStrada(adresaRequest.getStrada());
            adresa.setNumar(adresaRequest.getNumar());
            adresa.setLocalitate(adresaRequest.getLocalitate());
            // seteaza adresa updatata la student si salveaza studentul
            student.setAdresa(adresa);
            studentRepository.save(student);
            return ResponseEntity.ok("Adresa a fost actualizata cu succes.");
        } else {
            return ResponseEntity.badRequest().body("Studentul nu a fost gasit.");
        }
    }
    @DeleteMapping(path = "/{studentId}")
    public ResponseEntity<String> deleteStudentInfo(@PathVariable Integer studentId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();

            for (Materie materie: student.getCursuriAlese()) {
                 materie.getStudentiInrolati().remove(student);
                 materieRepository.save(materie);
            }
            studentRepository.delete(student);
            return ResponseEntity.ok("Studentul, adresa si detaliile aferente au fost sterse cu succes.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}



