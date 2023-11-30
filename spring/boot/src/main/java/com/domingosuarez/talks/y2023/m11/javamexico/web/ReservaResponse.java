package com.domingosuarez.talks.y2023.m11.javamexico.web;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReservaResponse {
    private Long reservaId;
    private ReservaCommand reservaInput;
}
