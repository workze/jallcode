package smartorm;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.lang3.ArrayUtils;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;

public class EntityService {

    private final QueryRunner queryRunner;

    public EntityService(DataSource dataSource) {
        this.queryRunner = new QueryRunner(dataSource);
    }

    public <T> Serializable insertEntityReturnPK(T entity) throws Exception{
        EntityMetadata metadata = EntityMetadata.getEntityMetadata(entity.getClass());
        String sql = metadata.getInsertEntitySql();
        System.out.println("insert sql:" + sql);
        Object[] params = metadata.getInsertEntityParams(entity);
        System.out.println("params:" + Arrays.toString(params));
        Serializable key = null;
        try (Connection conn = queryRunner.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            if (ArrayUtils.isNotEmpty(params)) {
                for (int i = 0; i < params.length; i++) {
                    pstmt.setObject(i + 1, params[i]);
                }
            }
            int rows = pstmt.executeUpdate();
            if (rows == 1) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    key = (Serializable) rs.getObject(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return key;
    }

    public void updateEntity(){

    }

    public void deleteEntity(){

    }

    public <T> T getEntity(Class<T> entityClass, Serializable id){
        T result;
        EntityMetadata metadata = EntityMetadata.getEntityMetadata(entityClass);
        Map<String, String> fieldMap = metadata.getColumnToPropertyMap();
        try {
            result = queryRunner.query(metadata.getQueryEntitySql(), new BeanHandler<T>(entityClass,
                    new BasicRowProcessor(new BeanProcessor(fieldMap))), id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static void main(String[] args) throws Exception {

        EntityService service = new EntityService(DB.h2DataSource());

        // test query
        Student student = service.getEntity(Student.class,2);

        // test insert
        Student s = new Student(0, "insertName", 18, "insertQQ", "otherName");
        service.insertEntityReturnPK(s);

        System.out.println(student);
    }
}
