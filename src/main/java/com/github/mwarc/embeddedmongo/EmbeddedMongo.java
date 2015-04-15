package com.github.mwarc.embeddedmongo;

import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Documented
@TestExecutionListeners({FongoTestExecutionListener.class, DependencyInjectionTestExecutionListener.class})
public @interface EmbeddedMongo {

    String dbName() default "myDbName";
}
