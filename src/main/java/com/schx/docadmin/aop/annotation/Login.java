package com.schx.docadmin.aop.annotation;

import java.lang.annotation.*;

@Target(value = {
        ElementType.TYPE,
        ElementType.METHOD
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Login {
}
