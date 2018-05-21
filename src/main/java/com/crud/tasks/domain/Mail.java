package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Mail {
    private String mailTo;
    private String subbject;
    private  String message;
    //private  String toCc;
}
