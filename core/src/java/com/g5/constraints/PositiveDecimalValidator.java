package com.g5.constraints;

import java.math.BigDecimal;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PositiveDecimalValidator implements ConstraintValidator<PositiveDecimal, BigDecimal> {

    @Override
    public void initialize(PositiveDecimal constraintAnnotation) {
    }

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        } else {
            return value.compareTo(BigDecimal.ZERO) == 1;
        }
    }

}
