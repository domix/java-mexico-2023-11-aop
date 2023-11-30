package com.domingosuarez.talks.y2023.m11.javamexico.web;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservaCommand {
    private String productId;
    private Integer cantidad;
    private Integer minutes;
}
