package raisetech.studentManagement.data;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter

public class StudentsCourses {
  private String id;
  private String studentsId;
  private String coursesName;
  private LocalDate startDate;
  private LocalDate endDate;

}
