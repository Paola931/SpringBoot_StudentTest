package com.example.studentTest.Controllers;

import com.example.studentTest.Entities.Student;
import com.example.studentTest.Repositories.StudentRepository;
import com.example.studentTest.Services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/createStudent")
    public @ResponseBody Student createStudent (@RequestBody Student student){
        return studentRepository.saveAndFlush(student);
    }
    @GetMapping("/getAllStudents")
    public @ResponseBody List<Student> getAllStudents(){
       return studentRepository.findAll();
    }
    @GetMapping("/getSingleStudent/{id}")
    public @ResponseBody Student getSingleStudent(@PathVariable Long id){
        Optional<Student> student = studentRepository.findById(id);
        if(student.isPresent()){
            return student.get();
        }else{
            return null;
        }
    }
    @PutMapping("/updateId")
    public @ResponseBody Student updateId(@RequestParam Long newId,
                            @RequestBody Student student){
        student.setId(newId);
        return studentRepository.saveAndFlush(student);
    }
    @PutMapping("/updateIsWorking/{id}")
    public @ResponseBody Student updateIsWorking (@PathVariable Long id,
                                    @RequestParam boolean working){
        return studentService.setIsWorkingStatus(id,working);
    }
    @DeleteMapping("/deleteSingleStudent/{id}")
    public void deleteSingleStudent(@PathVariable Long id){
        studentRepository.deleteById(id);
    }
}
