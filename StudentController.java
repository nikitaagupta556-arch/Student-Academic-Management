package com.kiet.student.controller;

import com.kiet.student.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final List<Student> students = new ArrayList<>();

    public StudentController() {
        students.add(new Student(101, "Rahul Sharma", "rahul@kiet.edu", "CSE(AI & ML)", 2, 8.65));
        students.add(new Student(102, "Priya Verma", "priya@kiet.edu", "CSE", 2, 9.10));
        students.add(new Student(103, "Ankit Singh", "ankit@kiet.edu", "IT", 3, 7.85));
        students.add(new Student(104, "Sneha Gupta", "sneha@kiet.edu", "CSE(AI)", 2, 9.25));
        students.add(new Student(105, "Rohan Mehta", "rohan@kiet.edu", "ECE", 4, 8.10));
    }

    private Student findById(int id) {
        for (Student s : students) {
            if (s.getStudentId() == id) return s;
        }
        return null;
    }

    private ResponseEntity<Map<String, String>> message(HttpStatus status, String msg) {
        return ResponseEntity.status(status).body(Map.of("message", msg));
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return students;
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<?> getStudentById(@PathVariable int studentId) {
        Student student = findById(studentId);
        if (student == null) return message(HttpStatus.NOT_FOUND, "Student not found");
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public ResponseEntity<?> addStudent(@RequestBody Student student) {
        if (findById(student.getStudentId()) != null)
            return message(HttpStatus.CONFLICT, "Student ID already exists");
        students.add(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(student);
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<?> updateStudent(@PathVariable int studentId,
                                            @RequestBody Student updated) {
        Student existing = findById(studentId);
        if (existing == null) return message(HttpStatus.NOT_FOUND, "Student not found");
        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setBranch(updated.getBranch());
        existing.setYear(updated.getYear());
        existing.setCgpa(updated.getCgpa());
        return ResponseEntity.ok(existing);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Map<String, String>> deleteStudent(@PathVariable int studentId) {
        Student existing = findById(studentId);
        if (existing == null) return message(HttpStatus.NOT_FOUND, "Student not found");
        students.remove(existing);
        return message(HttpStatus.OK, "Student deleted successfully");
    }

    @GetMapping("/search")
    public List<Student> searchStudents(@RequestParam String branch,
                                        @RequestParam int year) {
        List<Student> result = new ArrayList<>();
        for (Student s : students) {
            if (s.getBranch().toLowerCase().contains(branch.toLowerCase())
                    && s.getYear() == year) result.add(s);
        }
        return result;
    }

    @GetMapping("/topper")
    public List<Student> getToppers(@RequestParam double cgpa) {
        List<Student> result = new ArrayList<>();
        for (Student s : students) {
            if (s.getCgpa() >= cgpa) result.add(s);
        }
        return result;
    }
}
