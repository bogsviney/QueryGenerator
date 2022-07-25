package com.nazarov.query_generator.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Coloumn {
    String name() default "";
}
