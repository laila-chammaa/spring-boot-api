package com.example.demo.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//this will act as the data access layer talking to our database
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    //the method already does something like this: so no need to uncomment
    //@Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findByEmail(String email);
}
