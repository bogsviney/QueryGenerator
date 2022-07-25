package com.nazarov.query_generator.entity;

import com.nazarov.query_generator.annotation.Coloumn;
import com.nazarov.query_generator.annotation.Table;

@Table
public class Person {
    @Coloumn
    private int id;

    @Coloumn(name = "person_name")
    private String name;

    @Coloumn(name = "person_salary")
    private double salary;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
