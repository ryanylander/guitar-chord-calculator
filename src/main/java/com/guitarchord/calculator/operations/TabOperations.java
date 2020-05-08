package com.guitarchord.calculator.operations;

import java.util.HashSet;
import java.util.Set;

import com.guitarchord.calculator.model.Chord;
import com.guitarchord.calculator.model.GuitarChordTab;
import com.guitarchord.calculator.model.GuitarNote;
import com.guitarchord.calculator.model.GuitarStrings;
import com.guitarchord.calculator.model.Note;
import com.guitarchord.calculator.model.GuitarStrings.GuitarString;

public class TabOperations {

    public static Set<GuitarChordTab> getAllValidVoicings(Chord chord) {
        Set<GuitarChordTab> validVoicings = new HashSet<>();
        GuitarStrings guitarStrings = new GuitarStrings(6, 19);

        for(int ordinal = 1; ordinal <= 3; ordinal++) {
            Set<Chord> allInversions = ChordOperations.getAllInversions(chord.getRoot(), chord.getQuality());
            for (Chord inversion: allInversions) {
                GuitarString firstStringInChord = guitarStrings.getStringForOrdinal(ordinal);
                Set<Integer> matchingFrets = 
                    fretValues(firstStringInChord, 
                        ChordOperations.getDegree(inversion, 
                            inversion.getDegreeOfLowestNoteInChordInversion()));
                //Bit overkill to look at all frets so we need to narrow it down.
                GuitarNote bottomGuitarNote 
                    = new GuitarNote(firstStringInChord, matchingFrets.stream().findFirst().orElse(null), false);
                //do work here
            }
        }
        
        //TODO
        //Build a tree of possible voicings.
        //Each degree of the chord, starting from the degreeOfLowestNoteInChordInversion,
        //becomes a node. From each node, there are a set of possible positions on the next
        //string that satisfy one of the remaining notes in the chord.
        //At each stage in the tree,
        //1. If the open string satisfies one of the remaining notes in the chord, recurse.
        //2. If any of the notes within two frets of the note on the lowest string 
        //satisfies one of the remaining notes in the chord, recurse.
        //3. Try to mark the note as muted -- we allow up to two muted notes in a path.
        // This should carry some preference towards the fifth and, in 11s and 13s, the 9th, if possible.
        // Some will be taken already if we decide not to start on the lowest string.
        //4. If we are on the 5th string and there is a remaining chord degree required (5ths and 9ths optional)
        //that the 6th can't satisfy, do not add this node. Return.
        //5. If a node does not have any valid children, do not add it. Return.
        //6. If all these conditions are satisfied by the node, add it and return.

        //Loop over the first three strings as the starting string, and each inversion on each.
        
        return validVoicings;
    }

    //A node in the decision tree used to find possible chords.
    public class ChordNode {
        GuitarNote guitarNote;
        Set<ChordNode> children;

        public ChordNode(GuitarNote guitarNote) {
            this.guitarNote = guitarNote;
        }
    }

    public static Note noteValue(GuitarString string, int fret) {
        //find tuning of the open string
        Note tuning = string.getTuning();
        //get residue of open string note
        int tuningResidue = tuning.getResidue();
        //add the fret value
        int fretResidue = tuningResidue + fret;
        //modulo 12
        return Note.valueOf(fretResidue % 12);
    }

    public static Set<Integer> fretValues(GuitarString string, Note note) {
        //find tuning of the open string
        int numFrets = string.getNumFrets();
        Set<Integer> matchingFrets = new HashSet<>();
        for (int potentialFret = 0; potentialFret < numFrets; potentialFret++) {
            //brute forcing this one
            if (noteValue(string, potentialFret).getResidue() == note.getResidue()) {
                matchingFrets.add(potentialFret);
            }
        }
        return matchingFrets;
    }
    
}