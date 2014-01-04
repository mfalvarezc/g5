package com.g5.constraints.validators;

import java.lang.reflect.ParameterizedType;
import javax.validation.ConstraintValidatorContext;

public abstract class AbstractCrossParameterValidator<T> {
    
    private final Class<T> targetClass;
    
    public AbstractCrossParameterValidator() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        targetClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }
    
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value instanceof Object[]) {
            Object[] objects = (Object[]) value;
            boolean incorrectType = false;
            
            for (Object o : objects) {
                if (!targetClass.isInstance(o)) {
                    incorrectType = true;
                    break;
                } else if (!isValidValue(targetClass.cast(o), context)) {
                    return false;
                }
            }
            
            if (!incorrectType) {
                return true;
            }
            
        } else if (targetClass.isInstance(value)) {
            return isValidValue(targetClass.cast(value), context);
        }
        
        String targetClassName = targetClass.getSimpleName();
        
        throw new IllegalArgumentException("Only " + targetClassName + " and " + targetClassName + "[] are supported");
    }
    
    public abstract boolean isValidValue(T value, ConstraintValidatorContext context);
    
}
