package com.dulepov.spring.rest.boot_rest.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CheckEmailValidator implements ConstraintValidator <CheckEmail, String>{


    private String endOfEmail;

    @Override
    public void initialize(CheckEmail checkEmail) {

        endOfEmail=checkEmail.value();
    }

    @Override
    public boolean isValid(String inputValue, ConstraintValidatorContext constraintValidatorContext) {

            //если поле вообще не было передано
            if (inputValue==null){
                return false;
            }

            return inputValue.endsWith(endOfEmail);

    }

}
