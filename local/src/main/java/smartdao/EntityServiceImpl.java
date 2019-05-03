package smartdao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class EntityServiceImpl {
    
    public <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
        return null;
    }

    
    public <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        return null;
    }

    
    public <K, V> Map<K, V> queryEntityMap(Class<V> entityClass, String sql, Object... params) {
        return null;
    }

    
    public Object[] queryArray(String sql, Object... params) {
        return new Object[0];
    }

    
    public List<Object[]> queryArrayList(String sql, Object... params) {
        return null;
    }

    
    public Map<String, Object> queryMap(String sql, Object... params) {
        return null;
    }

    
    public List<Map<String, Object>> queryMapList(String sql, Object... params) {
        return null;
    }

    
    public <T> T queryColumn(String sql, Object... params) {
        return null;
    }

    
    public <T> List<T> queryColumnList(String sql, Object... params) {
        return null;
    }

    
    public <T> Map<T, Map<String, Object>> queryColumnMap(String column, String sql, Object... params) {
        return null;
    }

    
    public long queryCount(String sql, Object... params) {
        return 0;
    }

    
    public int update(String sql, Object... params) {
        return 0;
    }

    
    public Serializable insertReturnPK(String sql, Object... params) {
        return null;
    }
}
