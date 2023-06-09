package com.dulepov.spring.rest.boot_rest.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CommonServiceImpl implements CommonService{

    //вызываем базовый бин objectMapper
    @Autowired
    private ObjectMapper objectMapper;




    //PARTIAL UPDATE UNIVERSAL
    @Override
    @Transactional
    public <T> T applyPatchToEmployee(JsonMergePatch patchJson, T target, Class<T> targetClass) {

        //сериализуем обьект, который хотим изменить  в json
        JsonNode targetJson=objectMapper.convertValue(target, JsonNode.class);


        try {
            //мэтчим старый и новый jsonы воедино
            JsonNode matchedJson=patchJson.apply(targetJson);

            //десериализуем получившийся комбо-json в обьект класса
            return objectMapper.convertValue(matchedJson,targetClass);


        } catch (JsonPatchException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public void parseValidationResults(BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            //получение ошибок валидации
            List<FieldError> validErrors=bindingResult.getFieldErrors();
            List<String> errorsDescList=new ArrayList<>();

            for (FieldError error: validErrors){
                errorsDescList.add(error.getField()+" "+error.getDefaultMessage());
            }

            throw new ValidationException("Ошибки валидации для следующих полей: "+String.join(", ", errorsDescList));
        }
    }

}
