package smartdao;


import ch.qos.logback.core.joran.util.beans.BeanUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.dbutils.QueryRunner;
import org.hibernate.internal.util.beans.BeanInfoHelper;
import sun.swing.BeanInfoUtils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

@AllArgsConstructor
@NoArgsConstructor
public class SmartDao<T> {

    @Inject
    private DataSource dataSource;

    private QueryRunner queryRunner;

    @PostConstruct
    public void init(){
        queryRunner = new QueryRunner(dataSource);
    }

    public void save(T entity){
        Class<?> cls = entity.getClass();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(cls);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for(PropertyDescriptor propertyDescriptor: propertyDescriptors){
                propertyDescriptor.getReadMethod().invoke(entity, 1);
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    public void saveEntity(Class<T> cls, T entity){

    }

    public T getEntity(Class<T> cls, Serializable id){

        return null;
    }

    public T getEntity(Class<T> cls, Condition condition){

        return null;
    }


    public static void main(String[] args) {
        Condition condition = new Condition();
        condition.with("id",1);
    }
}
