package ormstaff.test;

import dbhelper.Column;
import lombok.Data;
import ormstaff.DBEntity;
import ormstaff.GenerateEndpoint;
import ormstaff.IDMode;

import javax.persistence.Id;

@DBEntity(name = "t_person")
@Data
@GenerateEndpoint(resourceName = "person")
@IDMode(mode = "db")
public class Person {
    @Id
    String id;

    @Column(name = "age")
    Integer age;
}


/*
*
*
*   api/server/v1/_core/person/
*   get  /person?id='123'
*   post /person { 'id':'1', 'age':'18' }
*   put  /person { 'id':'1', 'age':'20' }
*   delete /person { 'ids': [1,2,3] }
*
*
*
*
*
*
*
*
*/
