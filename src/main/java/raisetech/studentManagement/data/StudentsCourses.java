package raisetech.studentManagement.data;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class StudentsCourses {
  private String id;
  private String studentsId;
  private String coursesName;
  private Date startDate;
  private Date endDate;

}
