package com.guitarchord.calculator.model;

import lombok.Data;

@Data
public class GuitarChordTab {

    private GuitarNote[] notes = new GuitarNote[6];
    
    @Data
    public class GuitarNote {
        private String string;
        private int fret; //TODO upper, lower bounds, muted
        private boolean muted;
    }

}