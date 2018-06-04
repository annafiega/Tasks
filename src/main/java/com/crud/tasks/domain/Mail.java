package com.crud.tasks.domain;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Mail {
    private String mailTo;
    private String subject;
    private  String message;
    //private  String toCc;
}
