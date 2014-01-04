package com.g5.constraints;

import java.math.BigDecimal;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotZeroDecimalValidator implements ConstraintValidator<NotZeroDecimal, BigDecimal> {

    @Override
    public void initialize(NotZeroDecimal constraintAnnotation) {
    }

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        } else {
            return value.compareTo(BigDecimal.ZERO) != 0;
        }
    }

}
