package com.example.studentTest;

import com.example.studentTest.Entities.Student;
import com.example.studentTest.Repositories.StudentRepository;
import com.example.studentTest.Services.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles(value = "testdb")
public class StudentServiceTest {
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentRepository studentRepository;

    @Test
    void setIsWorkingStatusTest (){
        //creo studente
        Student student = new Student();
        student.setName("Mario");
        student.setSurname("Rossi");
        student.setWorking(false);
        Student saveStudent = studentRepository.saveAndFlush(student);
        //mi accerto che l'ID non sia null
        assertThat(saveStudent.getId()).isNotNull();
        //testo il corretto funzionamento di setIsWorking
        Student studentTest = studentService.setIsWorkingStatus(student.getId(),true);
        assertThat(studentTest.isWorking()).isTrue();
    }
}
