package com.guitarchord.calculator.model;

import java.util.Set;

import com.guitarchord.calculator.model.GuitarStrings.GuitarString;

import lombok.Data;

//A node in the decision tree used to find possible chords.
 @Data
 public class ChordNode {
    
    private GuitarChordTab tab; //Collects results; will be incomplete until we reach highest string
    private GuitarString string;
    private Set<ChordNode> children;

    public ChordNode(GuitarChordTab guitarChordTab, GuitarString string) {
        this.tab = guitarChordTab;
        this.string = string;
    }

    public void addChild(ChordNode node) {
        this.children.add(node);
    }
}