package dbhelper;

import java.util.Calendar;

public class Test {
    public static void main(String[] args) {
        Student student = new Student();
        student.setAge(1);
        student.setName("name");
        student.setBirthday(Calendar.getInstance());
        student.setStudentNo(1);

        new SimpleDbPipe<Student>().add(student);
        new SimpleDbPipe<Student>().update(student);
    }
}
