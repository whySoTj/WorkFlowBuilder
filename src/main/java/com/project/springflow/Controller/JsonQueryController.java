package com.project.springflow.Controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.project.springflow.Dto.DynamicObjDto;


@RestController
public class JsonQueryController {
    @PostMapping("/jsonQuery")
public Object handleRequest(@RequestBody DynamicObjDto requestBody) {
    // Process the requestBody (unknown structure)

    // For example, you might want to log or return the received object
    String jsonString = convertToJsonString(requestBody.getData());
    // String jsonPathQuery= "$[?(@.itemType == 'HAZMAT')]";
    // Use Jayway JsonPath to apply the JSON Path query on the JSON string
    return JsonPath.read(jsonString, requestBody.getJsonPathQuery());
}

private String convertToJsonString(JsonNode requestBody) {
    try {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(requestBody);
    } catch (JsonProcessingException e) {
        // Handle the exception (e.g., log it or throw a custom exception)
        e.printStackTrace();
        return null;
    }
}
}
