package com.ze.jdbi;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

import java.util.List;

public interface UserDao {
  @SqlUpdate("CREATE TABLE user (id INTEGER PRIMARY KEY, name VARCHAR)")
  void createTable();

  @SqlUpdate("INSERT INTO user(id, name) VALUES (?, ?)")
  void insertPositional(int id, String name);

  @SqlUpdate("INSERT INTO user(id, name) VALUES (:id, :name)")
  void insertNamed(@Bind("id") int id, @Bind("name") String name);

  @SqlUpdate("INSERT INTO user(id, name) VALUES (:id, :name)")
  void insertBean(@BindBean Object user);

  @SqlQuery("SELECT * FROM user ORDER BY name")
  @RegisterBeanMapper(Object.class)
  List<Object> listUsers();
}
