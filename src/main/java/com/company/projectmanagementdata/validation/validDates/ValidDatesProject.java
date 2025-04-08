package com.company.projectmanagementdata.validation.validDates;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ValidDatesProjectValidator.class)
public @interface ValidDatesProject {

    String message() default "Start date can not be later than end date!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
