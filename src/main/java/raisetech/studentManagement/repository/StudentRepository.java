package raisetech.studentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.studentManagement.data.Student;
import raisetech.studentManagement.data.StudentsCourses;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students WHERE is_deleted = false")
  List<Student> search();

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchStudentsCourses();

  // current max id (数字部分) 取得。データが0件の場合は 0 を返す
  @Select("SELECT COALESCE(MAX(CAST(SUBSTRING(id, 2) AS UNSIGNED)), 0) FROM students")
  int getMaxStudentIdNumber();

  @Select("SELECT COALESCE(MAX(CAST(SUBSTRING(id, 2) AS UNSIGNED)), 0) FROM students_courses")
  int getMaxCourseIdNumber();

  // ---- 登録用SQL ----

  @Insert("INSERT INTO students (id, name, kana_name, nickname, email_address, city, age, gender, remark, is_deleted) " +
      "VALUES (#{id}, #{name}, #{kanaName}, #{nickname}, #{emailAddress}, #{city}, #{age}, #{gender}, #{remark}, false)")
  void registerStudent(Student student);

  @Insert("INSERT INTO students_courses (id, students_id, courses_name, start_date, end_date) " +
      "VALUES (#{id}, #{studentsId}, #{coursesName}, #{startDate}, #{endDate})")
  void registerStudentCourse(StudentsCourses studentsCourses);

  // IDに一致する受講生を1件取得するSQL
  @Select("SELECT * FROM students WHERE id = #{id}")
  Student searchStudent(String id);
  //受講生更新
  @Update("UPDATE students SET name = #{name}, kana_name = #{kanaName}, nickname = #{nickname}, email_address = #{emailAddress},"
      + " city = #{city}, age = #{age}, gender = #{gender}, remark = #{remark}, is_deleted = #{deleted} WHERE id = #{id}")
  void updateStudent(Student student);

  // student_id に一致するコース一覧を取得するSQL
  @Select("SELECT * FROM students_courses WHERE students_id = #{studentsId}")
  List<StudentsCourses> searchStudentsCoursesByStudentsID(String studentId);
  //コース更新
  @Update("UPDATE students_courses SET courses_name = #{coursesName}, start_date = #{startDate}, end_date = #{endDate} WHERE id = #{id}")
  void updateStudentsCourses(StudentsCourses studentCourse);

}