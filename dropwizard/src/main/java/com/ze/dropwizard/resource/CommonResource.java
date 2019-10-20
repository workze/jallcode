package com.ze.dropwizard.resource;

import com.alibaba.fastjson.JSONObject;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.ze.dropwizard.data.Animal;
import com.ze.dropwizard.data.Person;
import io.dropwizard.jersey.caching.CacheControl;
import io.dropwizard.jersey.params.IntParam;
import io.swagger.annotations.Api;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.jvnet.hk2.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

//import org.jvnet.hk2.annotations.;

/**
 * Created by ZE-C on 2017/11/19.
 */
@Path("/common")
@Api("common")
@Produces(MediaType.APPLICATION_JSON)
@Service
public class CommonResource {

    DataSource dataSource;

    @PostConstruct
    public void init(){
        HikariConfig configuration = new HikariConfig();
        configuration.setJdbcUrl("jdbc:postgresql://127.0.0.1:5432/postgres");
        configuration.setDriverClassName("org.postgresql.Driver");
        configuration.setUsername("apple");
        configuration.setPassword("apple");
        configuration.setMaximumPoolSize(50);
        dataSource = new HikariDataSource(configuration);
    }

    private String _id = "123";
    private static final Logger log = LoggerFactory.getLogger(CommonResource.class);

    @Inject
    ValidateResource validateResource;

    public CommonResource() {
//        System.out.println(validateResource.getName());
    }

    @GET
    public JSONObject sayHello() {
        log.info("hi,i am info");
        log.debug("debug");
        log.warn("warn");
//        log.debug("hi,i am debug");
//        System.out.println(validateResource.getName());
        JSONObject res = new JSONObject();
        res.put("name", "common resource");
        return res;
    }

    @GET
    @Path("/get/{id}")
    @CacheControl(maxAge = 6, maxAgeUnit = TimeUnit.SECONDS)
    public String  sayHello2(@PathParam("id")IntParam id) {
        log.info("hi,i am info");
        log.debug("debug");
        log.warn("warn");
//        log.debug("hi,i am debug");
//        System.out.println(validateResource.getName());
        JSONObject res = new JSONObject();
        res.put("name", "common resource");
        return id.toString();
    }

    @POST
    @Path("person")
    public Response createPerson(@Valid Person person) {
        System.out.println();
        System.out.println("person in");
        return Response.ok(person).status(200).build();
    }

    @POST
    @Path("animal")
    public Response createPerson2(Animal para) {

        System.out.println("person in");
        System.out.println();
        return Response.ok(para).status(200).build();
    }

    @POST
    @Path("print_datasource")
    public Response printDatasource() {
        return Response.ok(dataSource.toString()).status(200).build();
    }

    @POST
    @Path("query_database")
    public Response queryDatasource() throws SQLException {
        QueryRunner runner = new QueryRunner(dataSource);
        Map<String, Object> query = runner.query("select 1", new MapHandler());
        return Response.ok(query).status(200).build();
    }

    @POST
    @Path("insert_terminal")
    public Response insertTerminal() throws SQLException {
        QueryRunner runner = new QueryRunner(dataSource);
        String serial = UUID.randomUUID().toString();
        int update = runner.update("INSERT INTO t_terminal ( \"terminalname\", \"modelno\", \"customerno\", \"desc\", \"createtime\", \"updatetime\", \"serial\") VALUES ( ?, 1, 1, 'I am desc', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?)", serial, serial);

        return Response.ok(update).status(200).build();
    }

    @POST
    @Path("insert_terminal_batch/{batchSize}")
    public Response insertTerminalBatch(@PathParam("batchSize")@DefaultValue("20") Integer batchSize) throws SQLException {
        QueryRunner runner = new QueryRunner(dataSource);
        Object[][] params = new Object[batchSize][2];
        for(int i=0; i<batchSize; i++){
            params[i][0] = UUID.randomUUID().toString();
            params[i][1] = UUID.randomUUID().toString();
        }
        //int[] res = runner.batch("insert into t_terminal (serial,terminalname,modelno,customerno，createtime, updatetime,\"desc\") values (?,?,1,1,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'I am desc...')", params);
        int[] res = runner.batch("INSERT INTO t_terminal ( \"terminalname\", \"modelno\", \"customerno\", \"desc\", \"createtime\", \"updatetime\", \"serial\") VALUES ( ?, 1, 1, 'I am desc', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?)", params);
        System.out.println(res);
        return Response.ok(res.length).status(200).build();
    }

    private Response response(String mes){
        Map<String,String>  res = new HashMap();
        res.put("message",mes);
        return Response.ok(res).status(200).build();
    }




}
