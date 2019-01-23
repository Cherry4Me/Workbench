package com.example.out;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
class StudentResource {
  @Autowired
  private StudentRepository studentRepository;

  @GetMapping(
      name = "/student"
  )
  public List<Student> retrieveAllStudent() {
    return studentRepository.findAll();
  }

  @GetMapping(
      name = "/student/{id}"
  )
  public Student retrieveStudent(@PathVariable long id) {
    Optional<Student> student = studentRepository.findById(id);
    if (!student.isPresent()) throw new IllegalArgumentException("id-" + id);
    return student.get();
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
    Optional<Student> formDb = studentRepository.findById(id);
    if (!formDb.isPresent()) return ResponseEntity.notFound().build();
    student.setId(id);
    Student save = studentRepository.save(student);
    return ResponseEntity.notFound().build();
  }
}
