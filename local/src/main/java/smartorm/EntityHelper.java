package smartorm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EntityHelper {

    private static Map<Class<?>,Map<String, String> > entityMap = new ConcurrentHashMap<>();

    public static <T> Map<String, String> getEntityMap(Class<T> entityClass) {

        return null;
    }
}
