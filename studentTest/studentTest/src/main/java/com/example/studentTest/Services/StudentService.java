package com.example.studentTest.Services;

import com.example.studentTest.Entities.Student;
import com.example.studentTest.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public Student setIsWorkingStatus(Long id, boolean working) {
        Optional<Student> studentId = studentRepository.findById(id);
        if (!studentId.isPresent()) return null;
        studentId.get().setWorking(working);
        return studentRepository.save(studentId.get());
    }


}
