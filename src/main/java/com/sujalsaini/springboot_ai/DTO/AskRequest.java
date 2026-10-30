package com.sujalsaini.springboot_ai.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class AskRequest {
    private String question;
    private String conversationId;
}