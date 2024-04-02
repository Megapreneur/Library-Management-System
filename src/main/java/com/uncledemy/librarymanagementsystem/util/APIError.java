package com.uncledemy.librarymanagementsystem.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
public class APIError {
    private HttpStatus status;

    private String message;


    public APIError(){                  }

    public APIError(HttpStatus status, Throwable ex){
        this();
        this.status =  status;
        this.message = "Unexpected Error";
    }
    public APIError(HttpStatus status, String message, Throwable ex){
        this();
        this.status =  status;
        this.message = message;
    }

    public String convertToJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper.writeValueAsString(this);
    }
}
