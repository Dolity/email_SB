package com.iamnbty.training.backend.model;

import lombok.Data;

import java.util.Date;

@Data
public class ChatMessage {

    private String  form;

    private String message;

    private Date created;

    public ChatMessage() {
        created = new Date();
    }

}
