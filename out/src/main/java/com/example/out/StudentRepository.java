package com.example.out;


import org.springframework.data.jpa.repository.JpaRepository;


interface StudentRepository extends JpaRepository<Student, Long> { }

