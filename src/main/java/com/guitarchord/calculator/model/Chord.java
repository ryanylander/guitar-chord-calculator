package com.guitarchord.calculator.model;

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
}