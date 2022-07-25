package com.nazarov.query_generator;

import com.nazarov.query_generator.annotation.*;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.StringJoiner;

public class DefaultQueryGenerator implements QueryGenerator {

    public static final String SELECT = "SELECT ";
    public static final String INSERT = "INSERT ";
    public static final String DELETE = "DELETE";
    public static final String INTO = "INTO ";
    public static final String FROM = " FROM ";
    public static final String WHERE = " WHERE ";
    public static final String VALUES = " VALUES ";
    public static final String EQUAL = " = ";
    public static final String COMA = ", ";
    public static final String SPACE = " ";
    public static final String APOSTROPHE = "'";
    public static final String OPEN_BRACKET = "(";
    public static final String CLOSE_BRACKET = ")";
    public static final String END_QUERY = ";";

    //FIXME: what if id column name is not "id"?
    public static final String ID = "id";

    @Override
    public String findAll(Class<?> type) {

        String tableName = getTableName(type);
        StringBuilder query = new StringBuilder(SELECT);
        StringJoiner columnNames = new StringJoiner(COMA);
        extractDeclaredFieldsNames(type, columnNames);
        query.append(columnNames);
        query.append(FROM);
        query.append(tableName);
        query.append(END_QUERY);

        return query.toString();
    }

    @Override
    public String findById(Class<?> type, Serializable id) {

        String tableName = getTableName(type);
        StringBuilder query = new StringBuilder(SELECT);
        StringJoiner columnNames = new StringJoiner(COMA);
        extractDeclaredFieldsNames(type, columnNames);
        query.append(columnNames);
        query.append(FROM);
        query.append(tableName);
        query.append(WHERE);
        query.append(ID);
        query.append(EQUAL);
        query.append(id);
        query.append(END_QUERY);

        return query.toString();
    }

    @Override
    public String deleteById(Class<?> type, Serializable id) {

        String tableName = getTableName(type);
        StringBuilder query = new StringBuilder(DELETE);
        query.append(FROM);
        query.append(tableName);
        query.append(WHERE);
        query.append(ID);
        query.append(EQUAL);
        query.append(id);
        query.append(END_QUERY);

        return query.toString();
    }

    @Override
    public String save(Object value) throws IllegalAccessException {

        String tableName = getTableName(value.getClass());
        StringJoiner columnNames = new StringJoiner(COMA);
        extractDeclaredFieldsNames(value.getClass(), columnNames);

        StringJoiner fieldValues = new StringJoiner(COMA, OPEN_BRACKET, CLOSE_BRACKET);
        for (Field declaredField : value.getClass().getDeclaredFields()) {
            extractDeclaredFieldsValues(declaredField, fieldValues, value);
        }

        StringBuilder query = new StringBuilder(INSERT);
        query.append(INTO);
        query.append(tableName);
        query.append(SPACE);
        query.append(OPEN_BRACKET);
        query.append(columnNames);
        query.append(CLOSE_BRACKET);
        query.append(VALUES);
        query.append(fieldValues);
        query.append(END_QUERY);

        return query.toString();
    }

    // TODO
    @Override
    public String set(Object value) {
        return null;
    }

    private String getTableName(Class<?> clazz) {

        Table tableAnnotation = clazz.getAnnotation(Table.class);
        if (tableAnnotation == null) {
            throw new IllegalArgumentException("Class is not ORM entity");
        }

        return tableAnnotation.name().isEmpty() ? clazz.getSimpleName() : tableAnnotation.name();
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

    //FIXME: need to check declared field type (char, String or other) to decide wrap with apostrophes or not
    private void extractDeclaredFieldsValues(Field declaredField, StringJoiner coloumnValues, Object value) throws IllegalAccessException {

        declaredField.setAccessible(true);
        Coloumn columnAnnotation = declaredField.getAnnotation(Coloumn.class);
        if (columnAnnotation != null) {
            coloumnValues.add(APOSTROPHE + declaredField.get(value) + APOSTROPHE);
        }
    }
}
