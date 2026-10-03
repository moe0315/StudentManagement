package raisetech.studentManagement.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.studentManagement.data.Student;
import raisetech.studentManagement.data.StudentsCourses;
import raisetech.studentManagement.domain.StudentDetail;
import raisetech.studentManagement.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    return repository.search();
  }

  public List<StudentsCourses> searchStudentsCourseList() {
    return repository.searchStudentsCourses();
  }

  @Transactional
  public void registerStudent(StudentDetail studentDetail) {
    // 1. 受講生ID（s005 など）の採番
    int nextStudentNum = repository.getMaxStudentIdNumber() + 1;
    String newStudentId = String.format("s%03d", nextStudentNum);

    Student student = studentDetail.getStudent();
    student.setId(newStudentId); // 採番したIDをセット

    repository.registerStudent(student); // 学生テーブルへ登録

    // 2. コースID（c005 など）の採番と登録
    int nextCourseNum = repository.getMaxCourseIdNumber() + 1;
    List<StudentsCourses> coursesList = studentDetail.getStudentsCourses();

    if (coursesList != null) {
      for (StudentsCourses course : coursesList) {
        String newCourseId = String.format("c%03d", nextCourseNum);

        course.setId(newCourseId);             // 例: c005
        course.setStudentsId(newStudentId);    // 例: s005

        repository.registerStudentCourse(course); // コーステーブルへ登録
        nextCourseNum++;
      }
    }
  }
}
