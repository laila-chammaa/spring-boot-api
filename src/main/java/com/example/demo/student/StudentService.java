package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

//this represents the service layer with all the business logic, will talk to the database

//to use without instantiating it
@Service
public class StudentService {

    //dependency injection! this will get the service layer to talk to the data layer
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        //returns the entire student table
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findByEmail(student.getEmail());
        //if someone tries to add a new student with an already taken email
        if (studentOptional.isPresent()) {
            //should have custom exceptions, but good for now
            throw new IllegalStateException("Email taken.");
        }

        if (!Student.validEmail(student.getEmail())) {
            throw new IllegalStateException("Invalid Email.");
        }

        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) {
            //should have custom exceptions, but good for now
            throw new IllegalStateException(String.format("No student found for the ID: %d.", studentId));
        }

        studentRepository.deleteById(studentId);
    }

    @Transactional //we don't need to use a query to update a student with @Transactional, the entity basically becomes a manages state
    public void updateStudent(Long studentId, String name, String email) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(String.format("No student found for the ID: %d.", studentId)));
        if (name != null && name.length() > 0 && !name.equals(student.getName())) {
            student.setName(name);
        }

        Optional<Student> studentOptional = studentRepository.findByEmail(email);
        //if someone tries to add a new student with an already taken email
        if (studentOptional.isPresent()) {
            //should have custom exceptions, but good for now
            throw new IllegalStateException("Email taken.");
        }

        if (email != null && Student.validEmail(email)) {
            student.setEmail(email);
        }

    }
}
