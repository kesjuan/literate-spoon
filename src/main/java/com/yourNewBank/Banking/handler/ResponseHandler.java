package com.yourNewBank.Banking.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(HttpStatus status, String message, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", status.value());
        map.put("message", message);
        map.put("data", responseObj);

        return new ResponseEntity<Object>(map, status);
    }

    public static ResponseEntity<Object> generateResponse(HttpStatus status, String message) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", status.value());
        map.put("message", message);

        return new ResponseEntity<Object>(map, status);
    }
}