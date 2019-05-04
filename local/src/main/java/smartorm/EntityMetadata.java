package smartorm;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class EntityMetadata<T> {

    private static Map<Class<?>, EntityMetadata> entityMetadataMap = new ConcurrentHashMap<>();
    private final Class<T> entityClass;
    /**
     * example: {name=name, QQNAME=qq, ID=id, AGE=age}
     * sorted by columnName
     */
    private Map<String, String> columnToPropertyMap = new TreeMap<>();
    private ImmutableList<String> normalColumnNames;
    private ImmutableList<String> normalPropertyNames;
    private ImmutableList<String> allColumnNames;
    private ImmutableList<String> allPropertyNames;
    private ColumnInfo idColumnInfo;
    private ImmutableList<ColumnInfo> normalColumnInfos;
    /**
     * example: t_student
     */
    private String tableName;
    /**
     * example: insert into t_table ( 'id', 'name', 'age') values (1 wang 18)
     */
    private String insertEntitySql;
    private String insertEntityWithIdSql;
    private String updateEntitySql;
    private String queryEntitySql;
    private String deleteEntitySql;

    private EntityMetadata(Class<T> entityClass) {
        this.entityClass = entityClass;
        initColumn();
        initColumnToPropertyMap();

        initNormalColumnList();
        initAllColumnList();

        initTableName();

        initInsertEntitySql();
        initInsertEntitySqlWithId();
        initUpdateEntitySql();
        initQueryEntitySql();
        initDeleteEntitySql();
    }

    public static EntityMetadata getEntityMetadata(Class<?> entityClass) {
        if (entityMetadataMap.get(entityClass) == null) {
            synchronized (EntityMetadata.class) {
                if (entityMetadataMap.get(entityClass) == null) {
                    EntityMetadata metadata = new EntityMetadata(entityClass);
                    entityMetadataMap.put(entityClass, metadata);
                }
            }
        }
        return entityMetadataMap.get(entityClass);
    }

    public static void main(String[] args) throws Exception {
        Student student = new Student(1, "wang", 18, "qq123", "otherName");
        EntityMetadata metadata = new EntityMetadata(Student.class);

        System.out.println(metadata.allColumnNames);
        System.out.println(metadata.allPropertyNames);
        System.out.println(metadata.normalColumnNames);
        System.out.println(metadata.normalPropertyNames);

        System.out.println(metadata.insertEntitySql);
        System.out.println(metadata.insertEntityWithIdSql);
        System.out.println(metadata.updateEntitySql);
        System.out.println(metadata.queryEntitySql);
        System.out.println(metadata.deleteEntitySql);

        System.out.println("insert params:" + Arrays.toString(metadata.getInsertEntityParams(student)));
    }

    public Object[] getInsertEntityParams(T entity) throws Exception {
        return getInsertEntityParams(entity, false);
    }

    public Object[] getInsertEntityParams(T entity, boolean withId) throws Exception {
        ImmutableList<String> columns;
        if (withId) {
            columns = allColumnNames;
        } else {
            columns = normalColumnNames;
        }
        List<Object> params = new LinkedList<>();
        EntityMetadata entityMetadata = EntityMetadata.getEntityMetadata(entity.getClass());
        Class entityClass = entity.getClass();
        for (String column : columns) {
            String property = columnToPropertyMap.get(column);
            Field field = entityClass.getDeclaredField(property);
            field.setAccessible(true);
            params.add(field.get(entity));
        }
        return params.toArray();
    }

    private void initColumn() {
        List<ColumnInfo> columnInfoList = new LinkedList<>();
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            // 过滤Transient列
            Transient trans = field.getDeclaredAnnotation(Transient.class);
            if (trans != null) {
                continue;
            }
            // 放入ID列
            if (field.getAnnotation(Id.class) != null) {
                idColumnInfo = getConlumnInfo(field);
            } else {
                // 放入其他列
                columnInfoList.add(getConlumnInfo(field));
            }
        }
        this.idColumnInfo = idColumnInfo;
        this.normalColumnInfos = ImmutableList.copyOf(columnInfoList);
    }

    private ColumnInfo getConlumnInfo(Field field) {
        String columnName = field.getName();
        Column column = field.getDeclaredAnnotation(Column.class);
        if (column != null && StringUtils.isNotEmpty(column.name())) {
            columnName = column.name();
        }
        return new ColumnInfo(field.getName(),
                field.getType().getName(),
                columnName);
    }

    private void initColumnToPropertyMap() {
        if (idColumnInfo != null) {
            columnToPropertyMap.put(idColumnInfo.getColumnName(), idColumnInfo.getPropertyName());
        }
        for (ColumnInfo columnInfo : normalColumnInfos) {
            columnToPropertyMap.put(columnInfo.getColumnName(), columnInfo.getPropertyName());
        }
    }

    private void initNormalColumnList() {
        List<String> columnList = new LinkedList<>();
        for (ColumnInfo columnInfo : normalColumnInfos) {
            columnList.add(columnInfo.getColumnName());
        }
        Collections.sort(columnList);
        normalColumnNames = ImmutableList.copyOf(columnList);
        List<String> propertyList = new LinkedList<>();
        for (String col : normalColumnNames) {
            propertyList.add(columnToPropertyMap.get(col));
        }
        normalPropertyNames = ImmutableList.copyOf(propertyList);
    }

    private void initAllColumnList() {
        List<String> allColumnList = new LinkedList<>();
        allColumnList.add(idColumnInfo.getColumnName());
        allColumnList.addAll(normalColumnNames);
        allColumnNames = ImmutableList.copyOf(allColumnList);

        List<String> allPropertyList = new LinkedList<>();
        allPropertyList.add(idColumnInfo.getPropertyName());
        allPropertyList.addAll(normalPropertyNames);
        allPropertyNames = ImmutableList.copyOf(allPropertyList);
    }

    private void initInsertEntitySql() {
        this.insertEntitySql = generateInsertSql(tableName, normalColumnNames);
    }

    private void initInsertEntitySqlWithId() {
        this.insertEntityWithIdSql = generateInsertSql(tableName, allColumnNames);
    }

    /**
     * 生成插入sql
     *
     * @param tableName   表名
     * @param columns 列名
     * @return value example: insert into t_table ( 'id', 'name', 'age') values (? , ? , ?)
     */
    private String generateInsertSql(String tableName, List<String> columns) {
        StringBuilder columnsStringBuilder = new StringBuilder("(");
        StringBuilder valuesStringBuilder = new StringBuilder("(");
        for (String column : columns) {
            columnsStringBuilder.append(column).append(",");
            valuesStringBuilder.append("?,");
        }
        columnsStringBuilder.deleteCharAt(columnsStringBuilder.length() - 1);
        columnsStringBuilder.append(")");
        valuesStringBuilder.deleteCharAt(valuesStringBuilder.length() - 1);
        valuesStringBuilder.append(")");

        StringBuilder sql = new StringBuilder();
        sql.append("insert into ").append(tableName).append(" ")
                .append(columnsStringBuilder)
                .append(" values ").append(valuesStringBuilder);
        return sql.toString();
    }

    // update t_table set name = ? , age = ? where id = ?
    private void initUpdateEntitySql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update ").append(tableName).append(" set ");
        for (ColumnInfo columnInfo : normalColumnInfos) {
            sql.append(columnInfo.getColumnName()).append("=?,");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(" where ")
                .append(idColumnInfo.getColumnName()).append("=?");
        updateEntitySql = sql.toString();
    }

    // select id,name,age from t_table where id = ?
    private void initQueryEntitySql() {
        StringBuilder sql = new StringBuilder();
        sql.append("select ");
        sql.append(idColumnInfo.getColumnName()).append(",");
        for (ColumnInfo columnInfo : normalColumnInfos) {
            sql.append(columnInfo.getColumnName()).append(",");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(" from ").append(tableName);
        sql.append(" where ").append(idColumnInfo.getColumnName()).append("=?");
        queryEntitySql = sql.toString();
    }

    private void initDeleteEntitySql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from ").append(tableName)
                .append(" where ")
                .append(idColumnInfo.columnName).append(" = ?");
        deleteEntitySql = sql.toString();
    }

    private void initTableName() {
        Table table = entityClass.getAnnotation(Table.class);
        this.tableName = table.name();
    }


    // ========== getters ===========

    public Map<String, String> getColumnToPropertyMap() {
        return columnToPropertyMap;
    }

    public String getInsertEntitySql() {
        return insertEntitySql;
    }

    public String getInsertEntityWithIdSql() {
        return insertEntityWithIdSql;
    }

    public String getUpdateEntitySql() {
        return updateEntitySql;
    }

    public String getQueryEntitySql() {
        return queryEntitySql;
    }

}
