package com.nano.demoware.hystrix.faulttolerance;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FaultTolerant {

	ServiceGroup group() default ServiceGroup.NONE;
	String command() default "";
}
