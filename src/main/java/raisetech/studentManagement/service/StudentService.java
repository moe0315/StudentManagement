package raisetech.studentManagement.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.studentManagement.data.Student;
import raisetech.studentManagement.data.StudentsCourses;
import raisetech.studentManagement.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    //絞り込みをする。年齢が30代の人のみを抽出する。
    //抽出したリストをコントローラーに返す。
    List<Student> allStudents = repository.search();
    return allStudents.stream()
        .filter(student -> student.getAge() >= 30 && student.getAge() <= 39)
        .toList();
  }

  public List<StudentsCourses> searchStudentsCourseList() {
    //絞り込み険悪で「Javaコース」のコース情報のみを抽出する。
    //抽出したリストをコントローラーに返す。
    List<StudentsCourses>  allStudentsCourses = repository.searchStudentsCourses();
    return allStudentsCourses.stream()
        .filter(course -> course.getCoursesName().contains("Java"))
        .toList();
  }
}
