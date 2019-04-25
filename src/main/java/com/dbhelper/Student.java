package com.dbhelper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@Table(name = "STUDENT")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student
{
    @Id(name = "student_id")
    private int studentNo;

    @Column(name = "name")
    private String name;

    @Column(name = "age", type = "int")
    private int age;

    @Column(name = "birthday", type = "Calendar")
    private Calendar birthday;
}
