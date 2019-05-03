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

public class EntityMetadata {

    private static Map<Class<?>, EntityMetadata> entityMetadataMap = new ConcurrentHashMap<>();

    public static EntityMetadata getEntityMetadata(Class<?> entityClass){
        if(entityMetadataMap.get(entityClass) == null){
            synchronized (EntityMetadata.class){
                if(entityMetadataMap.get(entityClass) == null){
                    EntityMetadata metadata = new EntityMetadata(entityClass);
                    entityMetadataMap.put(entityClass, metadata);
                }
            }
        }
        return entityMetadataMap.get(entityClass);
    }

    private final Class<?> entityClass;

    /**
     *
     * example: {name=name, QQNAME=qq, ID=id, AGE=age}
     */
    private Map<String,String> columnToPropertyMap = new TreeMap<>();

    private ColumnInfo idColumn;

    private ImmutableList<ColumnInfo> normalColumns;
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

    private EntityMetadata(Class<?> entityClass){
        this.entityClass = entityClass;
        initColumn();
        initColumnToPropertyMap();

        initTableName();

        initInsertEntitySql();
        initInsertEntitySqlWithId();
        initUpdateEntitySql();
        initQueryEntitySql();
        initDeleteEntitySql();
    }


    private void initColumn() {
        List<ColumnInfo> columnInfoList = new LinkedList<>();
        Field[] fields = entityClass.getDeclaredFields();
        for(Field field: fields){
            // 过滤Transient列
            Transient trans = field.getDeclaredAnnotation(Transient.class);
            if(trans != null) {
                continue;
            }
            // 放入ID列
            if(field.getAnnotation(Id.class) != null) {
                idColumn = getConlumnInfo(field);
            } else {
                // 放入其他列
                columnInfoList.add(getConlumnInfo(field));
            }
        }
        this.idColumn = idColumn;
        this.normalColumns = ImmutableList.copyOf(columnInfoList);
    }

    private ColumnInfo getConlumnInfo(Field field){
        String columnName = field.getName();
        Column column = field.getDeclaredAnnotation(Column.class);
        if(column != null && StringUtils.isNotEmpty(column.name())) {
            columnName = column.name();
        }
        return new ColumnInfo(field.getName(),
                field.getType().getName(),
                columnName);
    }

    private void initColumnToPropertyMap(){
        if(idColumn != null) {
            columnToPropertyMap.put(idColumn.getColumnName(), idColumn.getPropertyName());
        }
        for(ColumnInfo columnInfo: normalColumns){
            columnToPropertyMap.put(columnInfo.getColumnName(), columnInfo.getPropertyName());
        }
    }

    private void initInsertEntitySql(){
        this.insertEntitySql = generateInsertSql(tableName, normalColumns);
    }

    private void initInsertEntitySqlWithId() {
        List<ColumnInfo> columnInfos = new LinkedList<>();
        columnInfos.add(idColumn);
        columnInfos.addAll(normalColumns);
        this.insertEntityWithIdSql = generateInsertSql(tableName, columnInfos);
    }


    /**
     * 生成插入sql
     * @param tableName 表名
     * @param columnInfos 列
     * @return value example: insert into t_table ( 'id', 'name', 'age') values (? , ? , ?)
     */
    private String generateInsertSql(String tableName, List<ColumnInfo> columnInfos){
        StringBuilder columnsStringBuilder = new StringBuilder("(");
        StringBuilder valuesStringBuilder = new StringBuilder("(");
        for (ColumnInfo columnInfo: columnInfos) {
            columnsStringBuilder.append("'").append(columnInfo.getColumnName()).append("',");
            valuesStringBuilder.append("?,");
        }
        columnsStringBuilder.deleteCharAt(columnsStringBuilder.length()-1);
        columnsStringBuilder.append(")");
        valuesStringBuilder.deleteCharAt(valuesStringBuilder.length()-1);
        valuesStringBuilder.append(")");

        StringBuilder sql = new StringBuilder();
        sql.append("insert into ").append(tableName).append(" ")
                .append(columnsStringBuilder)
                .append(" values ").append(valuesStringBuilder);
        return sql.toString();
    }

    // update t_table set name = ? , age = ? where id = ?
    private void initUpdateEntitySql(){
        StringBuilder sql = new StringBuilder();
        sql.append("update ").append(tableName).append(" set ");
        for(ColumnInfo columnInfo : normalColumns){
            sql.append(columnInfo.getColumnName()).append("=?,");
        }
        sql.deleteCharAt(sql.length()-1);
        sql.append(" where ")
                .append(idColumn.getColumnName()).append("=?");
        updateEntitySql = sql.toString();
    }

    // select id,name,age from t_table where id = ?
    private void initQueryEntitySql(){
        StringBuilder sql = new StringBuilder();
        sql.append("select ");
        sql.append(idColumn.getColumnName()).append(",");
        for(ColumnInfo columnInfo:normalColumns){
            sql.append(columnInfo.getColumnName()).append(",");
        }
        sql.deleteCharAt(sql.length()-1);
        sql.append(" from ").append(tableName);
        sql.append(" where ").append(idColumn.getColumnName()).append("=?");
        queryEntitySql = sql.toString();
    }

    private void initDeleteEntitySql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from ").append(tableName)
                .append(" where ")
                .append(idColumn.columnName).append(" = ?");
        deleteEntitySql = sql.toString();
    }

    private void initTableName(){
        Table table = entityClass.getAnnotation(Table.class);
        this.tableName = table.name();
    }

    public Map<String, String> getColumnToPropertyMap() {
        return columnToPropertyMap;
    }


    // ========== getters ===========

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


    public static void main(String[] args) {
        Student student = new Student(1,"wang",18,"qq123","otherName");
        EntityMetadata metadata = new EntityMetadata(Student.class);

        System.out.println(metadata.insertEntitySql);
        System.out.println(metadata.insertEntityWithIdSql);
        System.out.println(metadata.updateEntitySql);
        System.out.println(metadata.queryEntitySql);
        System.out.println(metadata.deleteEntitySql);
    }

}
