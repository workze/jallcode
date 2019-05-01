package smartorm;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="student")
public class Student {
    @Id
    int id;

    String name;

    int age;

    @Column(name = "qqname")
    String qqName;

    @Transient
    String tmpName;
}
