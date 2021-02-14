package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.JUNE;

//used to fill our student table with data
@Configuration
public class StudentConfig {

    /*CommandLineRunner is a special bean that lets you execute some logic
     after the application context is loaded and started.*/
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student mariam = new Student("Mariam", "mariam.saed@gmail.com",
                    LocalDate.of(2004, JUNE, 20));
            Student asmaa = new Student("Asmaa", "asmaa.omar@gmail.com",
                    LocalDate.of(2000, JUNE, 14));

            repository.saveAll(List.of(mariam, asmaa));
        };
    }
}
