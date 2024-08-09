package com.example.studentgptdemo.web;

import com.example.studentgptdemo.entities.Student;
import com.example.studentgptdemo.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/register")
    public String showForm(Model model) {
        Student student = new Student();
        List<String> subjects = Arrays.asList("Math", "Science", "History", "Art");
        model.addAttribute("student", student);
        model.addAttribute("subjects", subjects);
        return "studentForm";
    }

//    @PostMapping("/register")
//    public String submitForm(Student student) {
//        studentRepository.save(student);
//        return "redirect:/students/register";
//    }

    @PostMapping("/register")
    public String submitForm(Student student) {
        if (student.getId() != null) {
            // Update existing student
            studentRepository.save(student);
        } else {
            // Create a new student
            studentRepository.save(student);
        }
        return "redirect:/students/list";
    }


    @GetMapping("/list")
    public String listStudents(Model model) {
        List<Student> students = studentRepository.findAll();
        model.addAttribute("students", students);
        return "studentList";
    }

    @GetMapping("/edit")
    public String showEditForm(Long id, Model model) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        List<String> subjects = Arrays.asList("Math", "Science", "History", "Art");
        model.addAttribute("student", student);
        model.addAttribute("subjects", subjects);
        return "studentForm";
    }

    @PostMapping("/edit")
    public String submitEditForm(Student student) {
        studentRepository.save(student);
        return "redirect:/students/list";
    }

}
