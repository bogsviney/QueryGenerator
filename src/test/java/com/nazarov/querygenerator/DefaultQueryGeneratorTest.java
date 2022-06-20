package com.nazarov.querygenerator;

import com.nazarov.querygenerator.entity.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultQueryGeneratorTest {
    @Test
    @DisplayName("TEST: Generate Select All")
    public void testGenerateSelectAll() {
        QueryGenerator queryGenerator = new DefaultQueryGenerator();
        String actualQuery = queryGenerator.findAll(Person.class);
        String expectedQuery = "SELECT id, person_name, person_salary FROM Person;";
        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    @DisplayName("TEST: Generate Select By Id")
    public void testGenerateSelectById() {
        QueryGenerator queryGenerator = new DefaultQueryGenerator();
        int id = 23;
        String actualQuery = queryGenerator.findById(Person.class, id);
        String expectedQuery = "SELECT id, person_name, person_salary FROM Person WHERE id = '23';";
        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    @DisplayName("TEST: Generate Delete By Id")
    public void testGenerateDeleteById() {
        QueryGenerator queryGenerator = new DefaultQueryGenerator();
        int id = 23;
        String actualQuery = queryGenerator.deleteById(Person.class, id);
        String expectedQuery = "DELETE FROM Person WHERE id = '23';";
        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    @DisplayName("TEST: Generate Insert query")
    public void testGenerateInsertQueryAkaSave() throws IllegalAccessException {
        QueryGenerator queryGenerator = new DefaultQueryGenerator();
        Person person = new Person();
        person.setId(23);
        person.setName("Michael Jordan");
        person.setSalary(1500000.0);
        String actualQuery = queryGenerator.save(person);
        String expectedQuery = "INSERT INTO Person (id, person_name, person_salary) VALUES ('23', 'Michael Jordan', '1500000.0');";
        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    @DisplayName("TEST: Generate Update query")
    public void testGenerateUpdateQueryAkaSet() throws IllegalAccessException {
        QueryGenerator queryGenerator = new DefaultQueryGenerator();
        Person person = new Person();
        person.setId(23);
        person.setName("Michael Jordan");
        person.setSalary(1500000.0);
        String actualQuery = queryGenerator.set(person);
        String expectedQuery = "UPDATE Person SET person_name = 'Michael Jordan', person_salary = '1500000.0' WHERE id = '23';";
        assertEquals(expectedQuery, actualQuery);
    }

}