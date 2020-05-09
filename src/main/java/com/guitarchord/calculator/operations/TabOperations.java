package com.guitarchord.calculator.operations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.management.InvalidAttributeValueException;

import com.guitarchord.calculator.model.Chord;
import com.guitarchord.calculator.model.ChordDegree;
import com.guitarchord.calculator.model.ChordNode;
import com.guitarchord.calculator.model.FretRange;
import com.guitarchord.calculator.model.GuitarChordTab;
import com.guitarchord.calculator.model.GuitarNote;
import com.guitarchord.calculator.model.GuitarStrings;
import com.guitarchord.calculator.model.Note;
import com.guitarchord.calculator.model.GuitarStrings.GuitarString;

public class TabOperations {

    public static final GuitarStrings GUITAR_STRINGS = new GuitarStrings(6, 19);
    private static final int HIGHEST_GUITAR_STRING_ORDINAL = GUITAR_STRINGS.getNumStrings();

    public static Set<GuitarChordTab> getAllValidVoicings(Chord chord) {
        Set<GuitarChordTab> validVoicings = new HashSet<>();

        for(int ordinal = 1; ordinal <= 3; ordinal++) {
            Set<Chord> allInversions = ChordOperations.getAllInversions(chord.getRoot(), chord.getQuality());
            for (Chord inversion: allInversions) {
                Set<ChordNode> trees = new HashSet<>();
                GuitarString firstStringInChord = GUITAR_STRINGS.getStringForOrdinal(ordinal);
                Note bassNote = ChordOperations.getDegree(inversion, inversion.getDegreeOfLowestNoteInChordInversion());
                Set<Integer> matchingFrets = 
                    fretValues(firstStringInChord, bassNote);
                for (int fret : matchingFrets) {
                    GuitarNote bottomGuitarNote 
                        = new GuitarNote(firstStringInChord, fret, false);
                    ChordNode root = new ChordNode(new GuitarChordTab(bottomGuitarNote), firstStringInChord);
                    buildTree(root, chord);
                    trees.add(root);
                }
                trees.forEach(tree -> {
                    traverseTree(tree, chord, validVoicings);
                });
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
        //Assume only 4 strings can be engaged. Bar chords aren't interesting.

        //Loop over the first three strings as the starting string, and each inversion on each.
        
        return validVoicings;
    }

    private static void traverseTree(ChordNode parent, Chord chord, Set<GuitarChordTab> validVoicings) {
        if (parent.getString().getOrdinal() == HIGHEST_GUITAR_STRING_ORDINAL) {
            if (allDegreesSatisfied(chord, parent.getTab())) {
                validVoicings.add(parent.getTab());
                return;
            }
        } else {
            parent.getChildren().forEach(child -> traverseTree(child, chord, validVoicings));
            return;
        }
    }

    private static void buildTree(ChordNode parent, Chord chord) {
        
        int parentString = parent.getString().getOrdinal();
        if (HIGHEST_GUITAR_STRING_ORDINAL == parentString || parent.getTab().getNumStringsEngaged() == 4) {
            return; //todo check this
        }
        //Move to next string up
        int currentStringOrdinal = parentString + 1;
        GuitarString currentString = GUITAR_STRINGS.getStringForOrdinal(currentStringOrdinal);
        Set<Note> unusedNotes = unusedNotes(chord, parent.getTab());
        if (unusedNotes.isEmpty()) {
            //Mute all remaining strings if we have discovered all notes.
            GuitarChordTab updatedChordTab = parent.getTab();
            updatedChordTab.setGuitarNote(new GuitarNote(currentString, -1, true));
            ChordNode newNode = new ChordNode(updatedChordTab, currentString);
            buildTree(newNode, chord);
            parent.addChild(newNode);
        }
        //1. If the open string satisfies one of the remaining notes in the chord, recurse.
        Note openStringNote = currentString.getTuning();
        if (unusedNotes.contains(openStringNote)) {
            GuitarNote guitarNote = new GuitarNote(currentString, 0, false);
            GuitarChordTab updatedChordTab = parent.getTab();
            updatedChordTab.setGuitarNote(guitarNote);
            ChordNode newNode = new ChordNode(updatedChordTab, currentString);
            buildTree(newNode, chord);
            parent.addChild(newNode);
        }
        Set<GuitarNote> matches = getMatchingNotesInFretRange(currentString, unusedNotes, getAvailableFretRange(parent.getTab()));
        if (matches.isEmpty()) {
            //2. If there are no matches on the next string, mute it and recurse.
            GuitarChordTab updatedChordTab = parent.getTab();
            updatedChordTab.setGuitarNote(new GuitarNote(currentString, -1, true));
            ChordNode newNode = new ChordNode(updatedChordTab, currentString);
            buildTree(newNode, chord);
            parent.addChild(newNode);
        } else {
            //3. If any of the notes within two frets of the note on the lowest string 
            //satisfies one of the remaining notes in the chord, recurse.
            matches.forEach(guitarNote -> {
                GuitarChordTab updatedChordTab = parent.getTab();
                updatedChordTab.setGuitarNote(guitarNote);
                ChordNode newNode = new ChordNode(updatedChordTab, currentString);
                buildTree(newNode, chord);
                parent.addChild(newNode);
            });
        }
    }

    private static Set<GuitarNote> getMatchingNotesInFretRange(GuitarString guitarString, Set<Note> unusedNotes, FretRange fretRange) {
        Set<GuitarNote> matches = new HashSet();
        for (int i = fretRange.getMin(); i <= fretRange.getMax(); i++){
            GuitarNote guitarNote = new GuitarNote(guitarString, i, false);
            if (unusedNotes.contains(guitarNote.getNote())) {
                matches.add(guitarNote);
            }
        }
        return matches;
    }

    private static FretRange getAvailableFretRange(GuitarChordTab tab) {
        try {
            GuitarNote lowestStringNote = tab.getLowestStringNote();
            FretRange range = new FretRange();
            int min = lowestStringNote.getFret() - 2;
            range.setMin(min = min < 0 ? 0 : min);
            int max = lowestStringNote.getFret() + 2;
            range.setMax(max = max > 19 ? 19 : max);
            return range;
        } catch(InvalidAttributeValueException e) {
            throw new RuntimeException(e);
        }
    }

    private static Set<Note> unusedNotes(Chord chord, GuitarChordTab guitarChordTab) {
        Set<Note> usedNotes = new HashSet(Arrays.asList(guitarChordTab.getNotes()));
        Set<Note> allNotes = chord.noteSet();
        Set<Note> unusedNotes = new HashSet();
        allNotes.stream().forEach(note -> {
            if (! usedNotes.contains(note)) {
                unusedNotes.add(note);
            }
        });
        return unusedNotes;
    }

    private static boolean allDegreesSatisfied(Chord chord, GuitarChordTab guitarChordTab) {
        boolean allDegreesSatisfied = true;
        Set<Note> notesFromTab = Arrays.stream(guitarChordTab.getNotes())
            .map(note -> note.getNote())
            .collect(Collectors.toSet());
        for (Note note : chord.noteSet()) {
            if (! notesFromTab.contains(note) && ! noteCanBeSkipped(chord.getHighestChordDegree(), chord.degreeOf(note))) {
                allDegreesSatisfied = false;
            }
        }
        return allDegreesSatisfied;
    }

    private static boolean noteCanBeSkipped(ChordDegree highestChordDegree, ChordDegree degreeOfNote) {
        if (highestChordDegree == ChordDegree.FIFTH) {
            return false;
        }
        Set<ChordDegree> skippableDegrees = new HashSet<>();
        if (highestChordDegree == ChordDegree.SEVENTH) {
            skippableDegrees.add(ChordDegree.FIFTH);
        } else if (highestChordDegree == ChordDegree.NINTH) {
            skippableDegrees.add(ChordDegree.FIFTH);
        } else if (highestChordDegree == ChordDegree.ELEVENTH) {
            skippableDegrees.add(ChordDegree.THIRD);
            skippableDegrees.add(ChordDegree.FIFTH);
        } else if (highestChordDegree == ChordDegree.THIRTEENTH) {
            skippableDegrees.add(ChordDegree.FIFTH);
            skippableDegrees.add(ChordDegree.NINTH);
            skippableDegrees.add(ChordDegree.ELEVENTH);
        }
        return skippableDegrees.contains(degreeOfNote);
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