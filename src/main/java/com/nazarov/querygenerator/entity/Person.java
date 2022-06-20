package com.nazarov.querygenerator.entity;

import com.nazarov.querygenerator.annotation.Coloumn;
import com.nazarov.querygenerator.annotation.Table;

@Table()
public class Person {

    @Coloumn
    private int id;

    @Coloumn(name="person_name")
    private String name;

    @Coloumn(name="person_salary")
    private double salary;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

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
