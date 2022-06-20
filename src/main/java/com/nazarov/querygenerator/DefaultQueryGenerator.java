package com.nazarov.querygenerator;

import com.nazarov.querygenerator.annotation.Coloumn;
import com.nazarov.querygenerator.annotation.Table;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.StringJoiner;

import static com.nazarov.querygenerator.SQLkKeyWords.*;

public class DefaultQueryGenerator implements QueryGenerator {

    //    "SELECT id, person_name, person_salary FROM Person;"
    @Override
    public String findAll(Class<?> type) {

        String tableName = getTableName(type);

        StringBuilder query = new StringBuilder(SELECT);
        StringJoiner coloumnNames = new StringJoiner(COMA);

        query.append(SPACE);
        extractDeclaredFieldsNames(type, coloumnNames);
        query.append(coloumnNames);
        query.append(SPACE);
        query.append(FROM);
        query.append(SPACE);
        query.append(tableName);
        query.append(END_QUERY);

        return query.toString();
    }

    //    "SELECT id, person_name, person_salary FROM Person WHERE id = '23';"
    @Override
    public String findById(Class<?> type, Serializable id) {
        String tableName = getTableName(type);
        StringBuilder query = new StringBuilder(SELECT);
        StringJoiner coloumnNames = new StringJoiner(COMA);

        query.append(SPACE);
        extractDeclaredFieldsNames(type, coloumnNames);
        query.append(coloumnNames);
        query.append(SPACE);
        query.append(FROM);
        query.append(SPACE);
        query.append(tableName);
        query.append(SPACE);
        query.append(WHERE);
        query.append(SPACE);
        query.append(ID);
        query.append(SPACE);
        query.append(EQUAL);
        query.append(SPACE);
        query.append(APOSTROPHE);
        query.append(id);
        query.append(APOSTROPHE);
        query.append(END_QUERY);

        return query.toString();
    }

    //    "DELETE FROM Person WHERE id = '23';"
    @Override
    public String deleteById(Class<?> type, Serializable id) {
        String tableName = getTableName(type);
        StringBuilder query = new StringBuilder(DELETE);
        StringJoiner coloumnNames = new StringJoiner(COMA);

        query.append(SPACE);
        query.append(FROM);
        query.append(SPACE);
        query.append(tableName);
        query.append(SPACE);
        query.append(WHERE);
        query.append(SPACE);
        query.append(ID);
        query.append(SPACE);
        query.append(EQUAL);
        query.append(SPACE);
        query.append(APOSTROPHE);
        query.append(id);
        query.append(APOSTROPHE);
        query.append(END_QUERY);

        return query.toString();
    }

    //    "INSERT INTO Person (id, person_name, person_salary) VALUES ('23', 'Michael Jordan', '1500000.0');"
    @Override
    public String save(Object value) throws IllegalAccessException {
        String tableName = getTableName(value.getClass());
        StringBuilder query = new StringBuilder(INSERT);
        StringJoiner coloumnNames = new StringJoiner(COMA);
        StringJoiner fieldValues = new StringJoiner(COMA);

        query.append(SPACE);
        query.append(INTO);
        query.append(SPACE);
        query.append(tableName);
        query.append(SPACE);
        query.append(OPEN_BRACKET);
        extractDeclaredFieldsNames(value.getClass(), coloumnNames);
        query.append(coloumnNames);
        query.append(CLOSE_BRACKET);
        query.append(SPACE);
        query.append(VALUES);
        query.append(SPACE);
        query.append(OPEN_BRACKET);

        for (Field declaredField : value.getClass().getDeclaredFields()) {
            extractDeclaredFieldsValues(declaredField, fieldValues, value);
        }

        query.append(fieldValues);
        query.append(CLOSE_BRACKET);
        query.append(END_QUERY);

        return query.toString();
    }

    @Override
    public String set(Object value) {
        String tableName = getTableName(value.getClass());
        StringBuilder query = new StringBuilder(UPDATE);
        StringJoiner coloumnNames = new StringJoiner(COMA);
        StringJoiner fieldValues = new StringJoiner(COMA);

        query.append(SPACE);
        query.append(tableName);
        query.append(SPACE);
        query.append(SET);
        query.append(SPACE);

        //Need to fix here
//        query.append("NAME_HERE");
//        query.append(SPACE);
//        query.append(EQUAL);
//        query.append(SPACE);
//        query.append("NAME_NEW_VALUE");

        query.append(COMA);
        query.append(SPACE);


        //Need to fix here 
//        query.append("SALARY_HERE");
//        query.append(SPACE);
//        query.append(EQUAL);
//        query.append(SPACE);
//        query.append("SALARY_NEW_VALUE");
//        query.append(SPACE);

        query.append(WHERE);
        query.append(SPACE);
        query.append(ID);
        query.append(SPACE);
        query.append(EQUAL);
        query.append(SPACE);
        query.append(APOSTROPHE);
        query.append("ID VALUE HERE");
        query.append(APOSTROPHE);
        query.append(END_QUERY);

        return query.toString();
    }


    private String getTableName(Class<?> clazz) {
        Table tableAnnotation = clazz.getAnnotation(Table.class);
        if (tableAnnotation == null) {
            throw new IllegalArgumentException("Class is not ORM entity");
        }
        String tableName = tableAnnotation.name().isEmpty() ? clazz.getSimpleName() : tableAnnotation.name();
        return tableName;
    }

    private void extractDeclaredFields(Field declaredField, StringJoiner coloumnNames) {
        declaredField.setAccessible(true);
        Coloumn coloumnAnnotation = declaredField.getAnnotation(Coloumn.class);
        if (coloumnAnnotation != null) {
            String coloumnName = coloumnAnnotation.name().isEmpty() ? declaredField.getName() : coloumnAnnotation.name();
            coloumnNames.add(coloumnName);
        }
    }

    private void extractDeclaredFieldsNames(Class<?> type, StringJoiner coloumnNames) {
        for (Field declaredField : type.getDeclaredFields()) {
            extractDeclaredFields(declaredField, coloumnNames);
        }
    }

    private void extractDeclaredFieldsValues(Field declaredField, StringJoiner coloumnValues, Object value) throws IllegalAccessException {
        declaredField.setAccessible(true);
        Coloumn coloumnAnnotation = declaredField.getAnnotation(Coloumn.class);
        if (coloumnAnnotation != null) {
            coloumnValues.add(APOSTROPHE + declaredField.get(value) + APOSTROPHE);
        }
    }
}
