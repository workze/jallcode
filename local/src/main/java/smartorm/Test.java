package smartorm;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.sqlite.SQLiteDataSource;
import org.sqlite.SQLiteJDBCLoader;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sound.midi.SoundbankResource;
import javax.sql.DataSource;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Map;

public class Test {

    public static void main(String[] args) throws SQLException {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        testEntity(entityManager);
        System.out.println("end");
    }

    public static void testEntity(EntityManager entityManager){
        entityManager.getTransaction().begin();
        // save...
        Student student = new Student();
        student.setId(0);
        student.setAge(18);
        student.setName("name");
        student.setQq("qqname");
        student.setOtherName("otherName");
        entityManager.persist(student);

        entityManager.getTransaction().commit();
        System.out.println(student.getId());


        System.out.println("end");
    }

    private void find(EntityManager entityManager){
//        Student student = new Student();
//        student.setId(4);
//        // get
//        student = entityManager.find(Student.class,student);
        GrvEntityService service = new GrvEntityService<Student>(DB.h2DataSource());
        int a = service.get();
        Student student = service.insertEntity(new Student());

    }
}
