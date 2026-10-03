package raisetech.studentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import raisetech.studentManagement.data.Student;
import raisetech.studentManagement.data.StudentsCourses;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
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

}
