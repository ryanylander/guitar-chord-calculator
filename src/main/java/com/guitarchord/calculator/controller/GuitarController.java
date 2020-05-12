package com.guitarchord.calculator.controller;

import java.util.HashSet;
import java.util.Set;

import com.guitarchord.calculator.model.Chord;
import com.guitarchord.calculator.model.ChordQuality;
import com.guitarchord.calculator.model.GuitarChordTab;
import com.guitarchord.calculator.model.GuitarTabResponse;
import com.guitarchord.calculator.model.Note;
import com.guitarchord.calculator.operations.TabOperations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/guitar")
public class GuitarController {

    @Autowired
    private ChordController chordController;

    @RequestMapping(value = "/tab", method = RequestMethod.GET)
    public @ResponseBody GuitarTabResponse getChordTablature(Note root, ChordQuality chordQuality) {
        Set<GuitarChordTab> validTabs = new HashSet<>();
        Set<Chord> validInversions = chordController.getAllInversions(root, chordQuality);

        validInversions.forEach(inversion -> {
            Set<GuitarChordTab> validVoicings = TabOperations.getAllValidVoicings(inversion);
            validTabs.addAll(validVoicings);
        });

        GuitarTabResponse response = new GuitarTabResponse();
        response.setValidTabs(validTabs);
        return response;
    }
    
}