package raisetech.studentManagement.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Student {

  private String id;
  private String name;
  private String kanaName;
  private String nickname;
  private String emailAddress;
  private String city;
  private int age;
  private String gender;
}