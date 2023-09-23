package com.iamnbty.training.backend.model;

import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Data
public class ChatMessageRequest {

    String message;

}
