package com.guitarchord.calculator.model;

import static com.guitarchord.calculator.operations.TabOperations.GUITAR_STRINGS;

import javax.management.InvalidAttributeValueException;

import lombok.Data;

@Data
public class GuitarChordTab {

    private GuitarNote[] notes = new GuitarNote[6];

    public GuitarChordTab(GuitarNote bottomStringNote) {
        if (bottomStringNote.getString().getOrdinal() == 2) {
            notes[0] = new GuitarNote(GUITAR_STRINGS.getStringForOrdinal(1), -1, true); 
        } else if (bottomStringNote.getString().getOrdinal() == 3) {
            notes[0] = new GuitarNote(GUITAR_STRINGS.getStringForOrdinal(1), -1, true); 
            notes[1] = new GuitarNote(GUITAR_STRINGS.getStringForOrdinal(2), -1, true); 
        }
        setGuitarNote(bottomStringNote);
    }

    public void setGuitarNote(GuitarNote guitarNote) {
        int index = guitarNote.getString().getOrdinal() - 1;
        notes[index] = guitarNote;
    }

    public GuitarNote getLowestStringNote() throws InvalidAttributeValueException {
        for (int i = 0; i < 3; i++) {
            if (! notes[i].isMuted()) {
                return notes[i];
            }
        }
        throw new InvalidAttributeValueException("The first three strings we all muted");
    }

    public int getNumStringsEngaged() {
        int total = 0;
        for (int i = 0; i< notes.length; i++) {
            if (! (null == notes[i]) && ! (notes[i].isMuted())) {
                total++;
            }
        }
        return total;
    }

}