package com.nazarov.query_generator;

import java.io.Serializable;

public interface QueryGenerator {

    String findAll(Class<?> type);

    String findById(Class<?> type, Serializable id);

    String deleteById(Class<?> type, Serializable id);

    String save(Object value) throws IllegalAccessException;

    String set(Object value);
}
