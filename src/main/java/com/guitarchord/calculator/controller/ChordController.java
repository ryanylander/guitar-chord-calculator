package com.guitarchord.calculator.controller;

import java.util.Set;

import com.guitarchord.calculator.model.Chord;
import com.guitarchord.calculator.model.ChordQuality;
import com.guitarchord.calculator.model.Note;
import com.guitarchord.calculator.operations.ChordOperations;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chord")
public class ChordController {

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public @ResponseBody Chord getChord(@RequestParam Note root, @RequestParam ChordQuality chordQuality) {
        return ChordOperations.buildChord(root, chordQuality);
    }

    @RequestMapping(value = "/get/all", method = RequestMethod.GET)
    public @ResponseBody Set<Chord> getAllInversions(@RequestParam Note root, @RequestParam ChordQuality chordQuality) {
        return ChordOperations.getAllInversions(root, chordQuality);
    }
    
}