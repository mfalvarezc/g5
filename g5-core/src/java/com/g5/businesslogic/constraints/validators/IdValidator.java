package com.g5.businesslogic.constraints.validators;

import com.g5.businesslogic.constraints.Id;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

@SupportedValidationTarget({ValidationTarget.ANNOTATED_ELEMENT,
    ValidationTarget.PARAMETERS})
public class IdValidator extends AbstractCrossParameterValidator<Long>
        implements ConstraintValidator<Id, Object> {

    @Override
    public void initialize(Id constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return super.isValid(value, context);
    }

    @Override
    public boolean isValidValue(Long value, ConstraintValidatorContext context) {
        return value != null && value > 0;
    }

}
