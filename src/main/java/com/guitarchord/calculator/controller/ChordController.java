package com.guitarchord.calculator.controller;

import com.guitarchord.calculator.model.Chord;
import com.guitarchord.calculator.model.ChordQuality;
import com.guitarchord.calculator.model.Note;
import com.guitarchord.calculator.operations.ChordOperations;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("/chord")
public class ChordController {

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Chord getChord(@RequestParam Note root, @RequestParam ChordQuality chordQuality) {
        return ChordOperations.buildChord(root, chordQuality);
    }
    
}