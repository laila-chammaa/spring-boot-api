package com.example.demo.student;

import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

//will have all the resources for our API, this class represents the API layer, shouldn't have business logic
@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

    //it has a studentService attribute to use the logic in that class
    private StudentService studentService;

    //the constructor will be passed a studentService
    //dependency injection! instead of initializing studentService with new StudentService()
    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @PostMapping
    public void registerNewStudent(@RequestBody Student student) { //it means we're taking the payload from the request sent to the api
        studentService.addNewStudent(student);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId) {
        studentService.deleteStudent(studentId);
    }

    @PutMapping(path = "{studentId}")
    public void updateStudent(@PathVariable("studentId") Long studentId,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) String email) {
        studentService.updateStudent(studentId, name, email);
    }
}
