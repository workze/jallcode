package smartorm

import javax.sql.DataSource

class GrvEntityService<T> {

    private DataSource dataSource;

    GrvEntityService(DataSource dataSource){
        this.dataSource = dataSource;
    }

    Student insertEntity(T entity){
        return entity
    }

    Integer get(){
        return 1
    }


}

