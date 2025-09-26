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
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", message);
        responseBody.put("data", entities);
        responseBody.put("statusCode", statusCode);
        responseBody.put("success", true);

        return new ResponseEntity<>(responseBody, statusCode);
    }

    public static ResponseEntity<?> createSuccessResponse(String message, HttpStatus statusCode, Object entity){
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", message);
        responseBody.put("data", entity);
        responseBody.put("statusCode", statusCode);
        responseBody.put("success", true);

        return new ResponseEntity<>(responseBody, statusCode);
    }

    public static ResponseEntity<?> createErrorResponse(String message, HttpStatus statusCode) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", message);
        responseBody.put("statusCode", statusCode);
        responseBody.put("success", false);


        return  new ResponseEntity<>(responseBody, statusCode);
    }
}
