package com.guitarchord.calculator.model;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public enum Note {

    A(0), 
    ASHARP(1), BFLAT(1),
    B(2),
    C(3), BSHARP(3), 
    CSHARP(4), DFLAT(4),
    D(5), 
    DSHARP(6), EFLAT(6),
    E(7), 
    F(8), ESHARP(8), 
    FSHARP(9), GFLAT(9),
    G(10), 
    GSHARP(11), AFLAT(11);

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

    public static Note valueOf(int residue) { //TODO these should support key-appropriate enharmonics
        if (residue < 0 || residue > 11) {
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