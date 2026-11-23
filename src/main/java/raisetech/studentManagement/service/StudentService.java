package raisetech.studentManagement.service;

import java.util.List;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.studentManagement.controller.converter.StudentConverter;
import raisetech.studentManagement.data.Student;
import raisetech.studentManagement.data.StudentCourse;
import raisetech.studentManagement.domain.StudentDetail;
import raisetech.studentManagement.repository.StudentRepository;

/**
 * 受講生情報を取り扱うサービスです。
 * 受講生の検索や登録・更新処理を行います。
 */
@Service
public class StudentService {

  private StudentRepository repository;
  private StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }
  /**
   * 受講生詳細の一覧検索です。
   * 全件検索を行うので、条件指定は行いません。
   *
   * @return　受講生詳細一覧（全件）
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentCourse> studentCourseList = repository.searchStudentsCourses();
    return converter.convertStudentDetails(studentList, studentCourseList);
  }
  /**
   * 受講生詳細の登録を行います。
   * 受講生と受講生コース情報を個別に登録し、受講生情報には受講生情報を紐づける値を設定します。
   *
   * @param studentDetail　受講生詳細
   * @return　登録情報を付与した受講生詳細
   */
  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    String newStudentId = initStudentId(studentDetail);
    initStudentCourse(studentDetail, newStudentId);
    return studentDetail;
  }
  /**
   * 受講生IDの自動採番・作成を行います。
   *
   * @param studentDetail　受講生詳細
   * @return　自動採番で作成した受講生ID
   */
  private @NonNull String initStudentId(StudentDetail studentDetail) {
    int nextStudentNum = repository.getMaxStudentIdNumber() + 1;
    String newStudentId = String.format("s%03d", nextStudentNum);
    Student student = studentDetail.getStudent();
    student.setId(newStudentId); // 採番したIDをセット
    repository.registerStudent(student); // 学生テーブルへ登録
    return newStudentId;
  }
  /**
   * 受講生コースIDの自動採番・作成を行い、受講生IDとの紐付けを行います。
   *
   * @param studentDetail　受講生詳細
   * @param newStudentId　自動採番で作成した受講生ID
   */
  private void initStudentCourse(StudentDetail studentDetail, String newStudentId) {
    int nextCourseNum = repository.getMaxCourseIdNumber() + 1;
    List<StudentCourse> coursesList = studentDetail.getStudentCourseList();

    if (coursesList != null) {
      for (StudentCourse course : coursesList) {
        String newCourseId = String.format("c%03d", nextCourseNum);

        course.setId(newCourseId);             // 例: c005
        course.setStudentsId(newStudentId);    // 例: s005

        repository.registerStudentCourse(course); // コーステーブルへ登録
        nextCourseNum++;
      }
    }
  }
  /**
   * 受講生詳細検索です。
   * IDに紐づく受講生情報を取得した後、その受講生に紐づく受講生コース情報を取得して設定します。
   *
   * @param id　受講生ID
   * @return　受講生詳細
   */

  public StudentDetail searchStudentDetail(String id) {
    Student student = repository.searchStudent(id);
    List<StudentCourse> courses = repository.searchStudentCourse(id);
    return new StudentDetail(student, courses);
  }

  /**
   * 受講生詳細の更新を行います。
   * 受講生と受講生コース情報をそれぞれ更新します。
   * @param studentDetail　受講生詳細
   */
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    studentDetail.getStudentCourseList()
        .forEach(course -> repository.updateStudentCourse(course));
  }
}
