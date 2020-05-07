package com.guitarchord.calculator.model;

import static com.guitarchord.calculator.model.DegreeQuality.MAJOR;
import static com.guitarchord.calculator.model.DegreeQuality.MINOR;
import static com.guitarchord.calculator.model.DegreeQuality.PERFECT;
import static com.guitarchord.calculator.model.DegreeQuality.DIMINISHED;
import static com.guitarchord.calculator.model.DegreeQuality.AUGMENTED;

public enum ChordQuality {
    //All supported chord qualities.

    //TRIADS
    MAJOR_TRIAD(MAJOR, PERFECT),
    MINOR_TRIAD(MINOR, PERFECT),
    AUGMENTED_TRIAD(MAJOR, AUGMENTED),
    DIMINISHED_TRIAD(MINOR, DIMINISHED),

    //SEVENS
    DOMINANT_SEVENTH(MAJOR, PERFECT, MINOR),
    MAJOR_SEVENTH(MAJOR, PERFECT, MAJOR),
    MINOR_MAJOR_SEVENTH(MINOR, PERFECT, MAJOR),
    MINOR_SEVENTH(MINOR, PERFECT, MINOR),
    AUGMENTED_MAJOR_SEVENTH(MAJOR, AUGMENTED, MAJOR),
    AUGMENTED_DOMINANT_SEVENTH(MAJOR, AUGMENTED, MINOR),
    HALF_DIMINISHED_SEVENTH(MINOR, DIMINISHED, MINOR),
    FULLY_DIMINISHED_SEVENTH(MINOR, DIMINISHED, DIMINISHED),
    SEVENTH_FLAT_FIVE(MAJOR, DIMINISHED, MINOR),

    //NINES
    MAJOR_NINTH(MAJOR, PERFECT, MAJOR, MAJOR),
    DOMINANT_NINTH(MAJOR, PERFECT, MINOR, MAJOR),
    DOMINANT_MINOR_NINTH(MAJOR, PERFECT, MINOR, MINOR),
    MINOR_MAJOR_NINTH(MINOR, PERFECT, MAJOR, MAJOR),
    MINOR_NINTH(MINOR, PERFECT, MINOR, MAJOR),
    AUGMENTED_MAJOR_NINTH(MAJOR, AUGMENTED, MAJOR, MAJOR),
    AUGMENTED_DOMINANT_NINTH(MAJOR, AUGMENTED, MINOR, MAJOR),
    HALF_DIMINISHED_NINTH(MINOR, DIMINISHED, MINOR, MAJOR),
    HALF_DIMINSHED_MINOR_NINTH(MINOR, DIMINISHED, MINOR, MINOR),
    DIMINISHED_NINTH(MINOR, DIMINISHED, DIMINISHED, MAJOR),
    DIMINISHED_MINOR_NINTH(MINOR, DIMINISHED, DIMINISHED, MINOR),

    //11S
    ELEVENTH(MAJOR, PERFECT, MINOR, MAJOR, PERFECT),
    MAJOR_ELEVENTH(MAJOR, PERFECT, MAJOR, MAJOR, PERFECT),
    MINOR_MAJOR_ELEVENTH(MINOR, PERFECT, MAJOR, MAJOR, PERFECT),
    MINOR_ELEVENTH(MINOR, PERFECT, MINOR, MAJOR, PERFECT),
    AUGMENTED_MAJOR_ELEVENTH(MAJOR, AUGMENTED, MAJOR, MAJOR, PERFECT),
    AUGMENTED_ELEVENTH(MAJOR, AUGMENTED, MINOR, MAJOR, PERFECT),
    HALF_DIMINISHED_ELEVENTH(MINOR, DIMINISHED, MINOR, MAJOR, PERFECT),
    DIMINISHED_ELEVENTH(MINOR, DIMINISHED, DIMINISHED, MAJOR, PERFECT),

    //13S
    MAJOR_THIRTEENTH(MAJOR, PERFECT, MAJOR, MAJOR, PERFECT, MAJOR),
    THIRTEENTH(MAJOR, PERFECT, MINOR, MAJOR, PERFECT, MAJOR),
    MINOR_MAJOR_THIRTEENTH(MINOR, PERFECT, MAJOR, MAJOR, PERFECT, MAJOR),
    MINOR_THIRTEENTH(MINOR, PERFECT, MINOR, MAJOR, PERFECT, MAJOR),
    AUGMENTED_MAJOR_THIRTEENTH(MAJOR, AUGMENTED, MAJOR, MAJOR, PERFECT, MAJOR),
    AUGMENTED_THIRTEENTH(MAJOR, AUGMENTED, MINOR, MAJOR, PERFECT, MAJOR),
    HALF_DIMINISHED_THIRTEENTH(MINOR, DIMINISHED, MINOR, MAJOR, PERFECT, MAJOR);

    private DegreeQuality thirdQuality;
    private DegreeQuality fifthQuality;
    private DegreeQuality seventhQuality;
    private DegreeQuality ninthQuality;
    private DegreeQuality eleventhQuality;
    private DegreeQuality thirteenthQuality;

    public DegreeQuality getQuality(ChordDegree degree) {
        switch(degree) {
            case THIRD:
                return thirdQuality;
            case FIFTH:
                return fifthQuality;
            case SEVENTH:
                return seventhQuality;
            case NINTH:
                return ninthQuality;
            case ELEVENTH:
                return eleventhQuality;
            case THIRTEENTH:
                return thirteenthQuality;
            default:
                throw new UnsupportedOperationException("Unsupported degree specified");
        }
    }

    //triad
    private ChordQuality(DegreeQuality thirdQuality,
                        DegreeQuality fifthQuality) {
        this.thirdQuality = thirdQuality;
        this.fifthQuality = fifthQuality;                   
    }

    //seventh
    private ChordQuality(DegreeQuality thirdQuality,
                        DegreeQuality fifthQuality,
                        DegreeQuality seventhQuality) {
        this.thirdQuality = thirdQuality;
        this.fifthQuality = fifthQuality;
        this.seventhQuality = seventhQuality;                       
    }

    //ninth
    private ChordQuality(DegreeQuality thirdQuality,
                        DegreeQuality fifthQuality,
                        DegreeQuality seventhQuality,
                        DegreeQuality ninthQuality) {
        this.thirdQuality = thirdQuality;
        this.fifthQuality = fifthQuality;
        this.seventhQuality = seventhQuality;
        this.ninthQuality = ninthQuality;
    }

    //eleventh
    private ChordQuality(DegreeQuality thirdQuality,
                        DegreeQuality fifthQuality,
                        DegreeQuality seventhQuality,
                        DegreeQuality ninthQuality,
                        DegreeQuality eleventhQuality) {
        this.thirdQuality = thirdQuality;
        this.fifthQuality = fifthQuality;
        this.seventhQuality = seventhQuality;
        this.ninthQuality = ninthQuality;
        this.eleventhQuality = eleventhQuality;
    }

    //thirteenth
    private ChordQuality(DegreeQuality thirdQuality,
                        DegreeQuality fifthQuality,
                        DegreeQuality seventhQuality,
                        DegreeQuality ninthQuality,
                        DegreeQuality eleventhQuality,
                        DegreeQuality thirteenthQuality) {
        this.thirdQuality = thirdQuality;
        this.fifthQuality = fifthQuality;
        this.seventhQuality = seventhQuality;
        this.ninthQuality = ninthQuality;
        this.eleventhQuality = eleventhQuality;
        this.thirteenthQuality = thirteenthQuality;
    }

}