package com.guitarchord.calculator.model;

import lombok.Data;

@Data
public class GuitarStrings {

    private String[] strings = new String[6];
    
    @Data
    public class String {
        private int degree;
        private Note tuning; //Some validation necessary for ranges
    }
}