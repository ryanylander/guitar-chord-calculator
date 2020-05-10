package com.guitarchord.calculator.model;

import com.guitarchord.calculator.model.GuitarStrings.GuitarString;
import com.guitarchord.calculator.operations.TabOperations;

import lombok.Data;

@Data
public class GuitarNote {
    private GuitarString string;
    private int fret; //TODO upper, lower bounds, muted
    private Note note; //We can compute this from the string + fret combination at any time
    private boolean muted;

    public GuitarNote(GuitarString string, int fret, boolean muted) {
        this.string = string;
        this.fret = fret;
        this.muted = muted;
        if (! muted) {
            this.note = TabOperations.noteValue(string, fret);
        } else {
            this.note = null;
        }
    }
}