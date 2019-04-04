package de.example;


import org.springframework.data.jpa.repository.JpaRepository;


interface StudentRepository extends JpaRepository<Student, Long> {}

