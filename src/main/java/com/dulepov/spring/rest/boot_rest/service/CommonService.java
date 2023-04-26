package com.dulepov.spring.rest.boot_rest.service;

import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import org.springframework.validation.BindingResult;

public interface CommonService {

    //PARTIAL UPDATE UNIVERSAL
    public <T> T applyPatchToEmployee(JsonMergePatch patchJson, T target, Class<T> targetClass);

    //обработка ошибок валидации
    public void parseValidationResults(BindingResult bindingResult);


}
