package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Optional<Student> studentOptional = this.studentRepository.findByEmail(student.getEmail());
        //if someone tries to add a new student with an already taken email
        if (studentOptional.isPresent()) {
            //should have custom exceptions, but good for now
            throw new IllegalStateException("Email taken.");
        }

        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(student.getEmail());
        //if the email doesn't matches the regex
        if (!matcher.matches()) {
            throw new IllegalStateException("Invalid Email.");
        }

        studentRepository.save(student);
    }

    public void deleteStudent(Long Id) {
        Optional<Student> studentOptional = this.studentRepository.findById(Id);
        //if that student doesn't exist
        if (!studentOptional.isPresent()) {
            //should have custom exceptions, but good for now
            throw new IllegalStateException("No student found for that ID.");
        }

        studentRepository.deleteById(Id);
    }
}
