package smartorm;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.sqlite.SQLiteDataSource;
import org.sqlite.SQLiteJDBCLoader;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws SQLException {
//        SQLiteDataSource dataSource = new SQLiteDataSource();
//        dataSource.setUrl("jdbc:sqlite:/Users/apple/Documents/github/jallcode/resource/devhelper.sqlite");
//
//        /*DefaultDataAccessor accessor = new DefaultDataAccessor(dataSource);
//        Map<String,Object> map =  accessor.queryMap("select * from student");*/
//
//        QueryRunner queryRunner = new QueryRunner(dataSource);
//
//        Student student = queryRunner.query("select id,name,age,qqname from student limit 1",
//                new BeanHandler<>(Student.class));


        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
        EntityManager entityManager = entityManagerFactory.createEntityManager();




        System.out.println("end");
    }
}
