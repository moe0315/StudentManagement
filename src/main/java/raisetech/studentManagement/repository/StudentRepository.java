package raisetech.studentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.studentManagement.data.Student;
import raisetech.studentManagement.data.StudentCourse;

/**
 * 受講生テーブル、受講生コース情報テーブルと紐づくRepositoryです。
 */
@Mapper
public interface StudentRepository {

  /**
   * 受講生の全件検索を行います。
   * @return　受講生一覧（全件）
   */
  List<Student> search();

  /**
   * 受講生のコース情報の全件検索を行います。
   * @return　受講生のコース情報（全件）
   */
  List<StudentCourse> searchStudentsCourses();

  /**
   * 受講生の検索を行います。
   *
   * @param id　受講生ID
   * @return　受講生
   */
  Student searchStudent(String id);

  /**
   * 受講生IDに紐づく受講生コース情報を検索します。
   * @param studentId　受講生ID
   * @return　受講生IDに紐づく受講生コース情報
   */
  List<StudentCourse> searchStudentCourse(String studentId);

  /**
   * 受講生IDの最大値(数字部分)取得します。データが0件の場合は0を返します。
   */
  int getMaxStudentIdNumber();

  /**
   * 受講生コースIDの最大値(数字部分)取得します。データが0件の場合は0を返します。
   */
  int getMaxCourseIdNumber();

  /**
   * 受講生を新規登録します。
   *
   * @param student　受講生
   */
  void registerStudent(Student student);

  /**
   * 受講生を更新します。
   *
   * @param student　受講生
   */
  void updateStudent(Student student);

  /**
   * 受講生コース情報を新規登録します。
   *
   * @param studentCourse　受講生コース情報
   */
  void registerStudentCourse(StudentCourse studentCourse);

  /**
   * 受講生コース情報のコース名を更新します。
   *
   * @param studentCourse　受講生コース情報
   */
  void updateStudentCourse(StudentCourse studentCourse);

}