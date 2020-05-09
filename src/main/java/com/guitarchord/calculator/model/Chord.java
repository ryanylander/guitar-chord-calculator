package com.guitarchord.calculator.model;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class Chord {
    private Note root;
    private Note third;
    private Note fifth;
    private Note seventh;
    private Note ninth;
    private Note eleventh;
    private Note thirteenth;
    private ChordQuality quality;
    private ChordDegree degreeOfLowestNoteInChordInversion;

    public Set<Note> noteSet() {
        Set<Note> notes = new HashSet<>();
        notes.add(root);
        notes.add(third);
        notes.add(fifth);
        addToSetIfNonNull(notes, seventh);
        addToSetIfNonNull(notes, ninth);
        addToSetIfNonNull(notes, eleventh);
        addToSetIfNonNull(notes, thirteenth);
        return notes;
    }

    public ChordDegree degreeOf(Note note) {
        if (note.getResidue() == root.getResidue()) {
            return ChordDegree.ROOT;
        }
        if (note.getResidue() == third.getResidue()) {
            return ChordDegree.THIRD;
        }
        if (note.getResidue() == fifth.getResidue()) {
            return ChordDegree.FIFTH;
        }
        if (seventh != null && note.getResidue() == seventh.getResidue()) {
            return ChordDegree.SEVENTH;
        }
        if (ninth != null && note.getResidue() == ninth.getResidue()) {
            return ChordDegree.NINTH;
        }
        if (eleventh != null && note.getResidue() == eleventh.getResidue()) {
            return ChordDegree.ELEVENTH;
        }
        if (thirteenth != null && note.getResidue() == thirteenth.getResidue()) {
            return ChordDegree.THIRTEENTH;
        }
        throw new InvalidParameterException("Note does not match any chord degree");
    }

    public ChordDegree getHighestChordDegree() {
        if (null != thirteenth) {
            return ChordDegree.THIRTEENTH;
        } else if (null != eleventh) {
            return ChordDegree.ELEVENTH;
        } else if (null != ninth) {
            return ChordDegree.NINTH;
        } else if (null != seventh) {
            return ChordDegree.SEVENTH;
        } else {
            return ChordDegree.FIFTH;
        }
    }

    private void addToSetIfNonNull(Set<Note> notes, Note note) {
        if (null != note) {
            notes.add(note);
        }
    }
}