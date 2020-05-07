package com.guitarchord.calculator.operations;

import com.guitarchord.calculator.model.Chord;
import com.guitarchord.calculator.model.ChordDegree;
import com.guitarchord.calculator.model.ChordQuality;
import com.guitarchord.calculator.model.DegreeQuality;
import com.guitarchord.calculator.model.Note;

public class ChordOperations {

    public static Chord buildChord(Note root, ChordQuality chordQuality) {
        Chord chord = new Chord();
        chord.setQuality(chordQuality);
        chord.setRoot(root);
        chord.setThird(generateThird(root, chordQuality));
        chord.setFifth(generateFifth(root, chordQuality));
        chord.setSeventh(generateSeventh(root, chordQuality));
        chord.setNinth(generateNinth(root, chordQuality));
        chord.setEleventh(generateEleventh(root, chordQuality));
        chord.setThirteenth(generateThirteenth(root, chordQuality));
        chord.setDegreeOfLowestNoteInChordInversion(ChordDegree.ROOT);
        return chord;
    }

    private static Note generateThird(Note root, ChordQuality chordQuality) {

        DegreeQuality degreeQuality = chordQuality.getQuality(ChordDegree.THIRD);
        int halfStepsAboveRoot = 0; //-1? -infinity?

        switch (degreeQuality) {
            case MAJOR:
                halfStepsAboveRoot = 4;
                break;
            case MINOR:
                halfStepsAboveRoot = 3;
                break;
            default:
                throw new UnsupportedOperationException("Unsupported quality found for degree: " + ChordDegree.THIRD.name());
        }
        
        int residue = (root.getResidue() + halfStepsAboveRoot) % 12;
        
        return Note.valueOf(residue);
    }

    private static Note generateFifth(Note root, ChordQuality chordQuality) {
        DegreeQuality degreeQuality = chordQuality.getQuality(ChordDegree.THIRD);
        int halfStepsAboveRoot = 0; //-1? -infinity?

        switch (degreeQuality) {
            case PERFECT:
                halfStepsAboveRoot = 7;
                break;
            case DIMINISHED:
                halfStepsAboveRoot = 6;
                break;
            default:
                throw new UnsupportedOperationException("Unsupported quality found for degree: " + ChordDegree.FIFTH.name());
        }
        
        int residue = (root.getResidue() + halfStepsAboveRoot) % 12;
        
        return Note.valueOf(residue);
    }

    private static Note generateSeventh(Note root, ChordQuality chordQuality) {
        DegreeQuality degreeQuality = chordQuality.getQuality(ChordDegree.THIRD);
        int halfStepsAboveRoot = 0; //-1? -infinity?

        switch (degreeQuality) {
            case MAJOR:
                halfStepsAboveRoot = 11;
                break;
            case MINOR:
                halfStepsAboveRoot = 10;
                break;
            case DIMINISHED:
                halfStepsAboveRoot = 6;
                break;
            default:
                throw new UnsupportedOperationException("Unsupported quality found for degree: " + ChordDegree.FIFTH.name());
        }
        
        int residue = (root.getResidue() + halfStepsAboveRoot) % 12;
        
        return Note.valueOf(residue);
    }

    private static Note generateNinth(Note root, ChordQuality chordQuality) {
        DegreeQuality degreeQuality = chordQuality.getQuality(ChordDegree.THIRD);
        int halfStepsAboveRoot = 0; //-1? -infinity?

        switch (degreeQuality) {
            case MAJOR:
                halfStepsAboveRoot = 14; //or 2
                break;
            case MINOR:
                halfStepsAboveRoot = 13; //or 1
                break;
            default:
                throw new UnsupportedOperationException("Unsupported quality found for degree: " + ChordDegree.FIFTH.name());
        }
        
        int residue = (root.getResidue() + halfStepsAboveRoot) % 12;
        
        return Note.valueOf(residue);
    }

    private static Note generateEleventh(Note root, ChordQuality chordQuality) {
        //Only support perfect 11s
        if (chordQuality.getQuality(ChordDegree.ELEVENTH) != DegreeQuality.PERFECT) {
            throw new UnsupportedOperationException("Unsupported quality found for degree: " + ChordDegree.ELEVENTH.name());
        } 

        int halfStepsAboveRoot = 17; //or 5
        int residue = (root.getResidue() + halfStepsAboveRoot) % 12;

        return Note.valueOf(residue);
    }

    private static Note generateThirteenth(Note root, ChordQuality chordQuality) {
        //Only support major 13s
        if (chordQuality.getQuality(ChordDegree.THIRTEENTH) != DegreeQuality.MAJOR) {
            throw new UnsupportedOperationException("Unsupported quality found for degree: " + ChordDegree.THIRTEENTH.name());
        } 
        int halfStepsAboveRoot = 21; // or 9
        int residue = (root.getResidue() + halfStepsAboveRoot) % 12;

        return Note.valueOf(residue);
    }

}