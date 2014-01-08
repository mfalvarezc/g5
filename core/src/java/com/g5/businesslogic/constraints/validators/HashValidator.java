package com.g5.businesslogic.constraints.validators;

import com.g5.businesslogic.constraints.Hash;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

@SupportedValidationTarget({ValidationTarget.ANNOTATED_ELEMENT,
    ValidationTarget.PARAMETERS})
public class HashValidator extends AbstractCrossParameterValidator<String>
        implements ConstraintValidator<Hash, Object> {

    private static final int LENGTH = 32;

    @Override
    public void initialize(Hash constraintAnnotation) {
    }

    @Override
    public boolean isValidValue(String value, ConstraintValidatorContext context) {
        if (value.length() != LENGTH) {
            return false;
        } else {
            return Util.isBase64(value);
        }
    }

}
