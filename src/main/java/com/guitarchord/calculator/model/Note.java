package com.guitarchord.calculator.model;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public enum Note {

    A(1), 
    ASHARP(2), BFLAT(2),
    B(3),
    C(4), BSHARP(4), 
    CSHARP(5), DFLAT(5),
    D(6), 
    DSHARP(7), EFLAT(7),
    E(8), 
    F(9), ESHARP(9), 
    FSHARP(10), GFLAT(10),
    G(11), 
    GSHARP(12), AFLAT(12);

    private static Map<Integer, Set<Note>> map = new HashMap();
    static {
        for (Note note : Note.values()) {
            if (null == map.get(note.residue)) {
                Set<Note> enharmonicNotes = new HashSet<>();
                enharmonicNotes.add(note);
                map.put(note.residue, enharmonicNotes);
            } else {
                Set<Note> enharmonicNotes = map.get(note.residue);
                enharmonicNotes.add(note);
                map.put(note.residue, enharmonicNotes);
            }
        }
    }

    private final int residue; //could also be "congruence class" -- for modulo comparison
    public int getResidue() {return residue;}
    private Note(int residue) {
        this.residue = residue;
    }

    public static Note valueOf(int residue) {
        if (residue < 1 || residue > 12) {
            throw new InvalidParameterException("There are only 12 notes in western music theory");
        }
        return map.get(residue).stream().findAny().orElse(null);
    }

    public Note enharmonic() {
        switch(this) {
            //sharps to flats
            case ASHARP:
                return BFLAT;
            case CSHARP:
                return DFLAT;
            case DSHARP:
                return EFLAT;
            case FSHARP:
                return GFLAT;
            case GSHARP:
                return AFLAT;
            
            //flats to sharps
            case BFLAT:
                return ASHARP;
            case DFLAT:
                return CSHARP;
            case EFLAT:
                return DSHARP;
            case GFLAT:
                return FSHARP;
            case AFLAT:
                return GSHARP;

            //natural to modified
            case C:
                return BSHARP;
            case F:
                return ESHARP;
            
            //modified to natural
            case BSHARP:
                return C;
            case ESHARP:
                return F;
            
            default: //TODO will eventually need to support double sharps / flats.
                throw new UnsupportedOperationException("Note has no enharmonic equivalent without using double-sharps or double-flats.");
        }
    }
}