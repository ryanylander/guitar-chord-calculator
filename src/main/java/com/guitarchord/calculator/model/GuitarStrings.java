package com.guitarchord.calculator.model;

import lombok.Data;

@Data
public class GuitarStrings {

    private int numStrings;
    private int numFrets;
    private GuitarString[] strings;

    public GuitarString getStringForOrdinal(int ordinal) {
        //There is no 0th string so we obscure the zero indexed array by subtracting one
        return strings[ordinal - 1];
    }

    public GuitarStrings(int numStrings, int numFrets) {
        /*Default constructor builds a classical guitar with standard tuning.*/
        // int numFrets = 19;
        // int numStrings = 6;
        this.numStrings = numStrings;
        this.numFrets = numFrets;
        this.strings = new GuitarString[numStrings];
        for(int ordinal = 1; ordinal <= numStrings; ordinal++) {
            Note tuning = null;
            switch(ordinal) {
                case 1:
                    tuning = Note.E;
                    break;
                case 2:
                    tuning = Note.A;
                    break;
                case 3:
                    tuning = Note.D;
                    break;
                case 4:
                    tuning = Note.G;
                    break;
                case 5:
                    tuning = Note.B;
                    break;
                case 6:
                    tuning = Note.E;
                    break;
            }
            //There is no 0th string so we obscure the zero indexed array by subtracting one
            strings[ordinal - 1] = new GuitarString(ordinal, tuning, numFrets); 
        }
    }
    
    @Data
    public class GuitarString {

        public GuitarString(int ordinal, Note tuning, int numFrets) {
            this.ordinal = ordinal;
            this.tuning = tuning;
            this.numFrets = numFrets;
        }

        private int ordinal; //1 through 6, unless support is added for more strings
        private Note tuning; //Some validation necessary for ranges
        private int numFrets; //Standard classical guitars have 19 frets and electric guitars between 21 and 24 frets
    }
}