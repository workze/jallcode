package smartorm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name="STUDENT")
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    int id;

    String name;

    int age;

    String qqName;

}
