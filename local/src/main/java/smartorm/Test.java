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
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws SQLException {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        System.out.println("inited");
        entityManager.getTransaction().begin();
        Student student = new Student();
        student.setAge(18);
        student.setName("name");
        student.setQqName("qqname");
        entityManager.persist(student);
        entityManager.getTransaction().commit();

        System.out.println(student.getId());

        System.out.println("end");
    }
}
