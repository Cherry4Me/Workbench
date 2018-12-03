package com.example.out;

import java.lang.Object;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
class StudentResource {
  @Autowired
  private StudentRepository studentRepository;

  @GetMapping(
      name = "/"student""
  )
  public List<Student> retrieveAllStudent() {
    return studentRepository.findAll();
  }

  @GetMapping(
      name = "/student/{id}"
  )
  public Student retrieveStudent(@PathVariable long id) {
    Optional<Student> id = studentRepository.findById(id);
    if (!id.isPresent()) throw new IllegalArgumentException("id-" + id);
    return id.get();
  }

  @DeleteMapping(
      name = "/student/{id}"
  )
  public void deleteStudent(@PathVariable long id) {
    studentRepository.deleteById(id);
  }

  @PostMapping(
      name = "/student"
  )
  public ResponseEntity<Object> createStudent(@RequestBody Student student) {
    Student savedStudent = studentRepository.save(student);
    URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(
                    savedStudent
                        .getId())
                .toUri();
    return ResponseEntity.created(location).build();
  }

  @PutMapping(
      name = "/student/{id}"
  )
  ResponseEntity<Object> updateStudent(@RequestBody Student student, @PathVariable long id) {
    Optional<Student> id = studentRepository.findById(id);
    if (!id.isPresent()) return ResponseEntity.notFound().build();
    student.setId(id);
    studentRepository.save(student);
    return ResponseEntity.notFound().build();
  }
}
