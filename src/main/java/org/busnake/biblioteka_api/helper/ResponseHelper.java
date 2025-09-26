package org.busnake.biblioteka_api.helper;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHelper {

    // TODO: Criar classe de Response para servir todas as informações da resposta em um só objeto ao invés de usar map
    public static ResponseEntity<?> createSuccessResponse(String message, HttpStatus statusCode, EntityModel<?> entity){
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("message", message);
        requestBody.put("data", entity);
        requestBody.put("statusCode", statusCode);
        requestBody.put("success", true);

        return new ResponseEntity<>(requestBody, statusCode);
    }

    public static ResponseEntity<?> createSuccessResponse(String message, HttpStatus statusCode, CollectionModel<?> entities){
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("message", message);
        requestBody.put("data", entities);
        requestBody.put("statusCode", statusCode);
        requestBody.put("success", true);

        return new ResponseEntity<>(requestBody, statusCode);
    }
}
