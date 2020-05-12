package com.guitarchord.calculator.model;

import java.util.Set;

import lombok.Data;

@Data
public class GuitarTabResponse {

    public Set<GuitarChordTab> validTabs;

    public GuitarTabResponse() {
    }

    public GuitarTabResponse(Set<GuitarChordTab> validTabs) {
        this.validTabs = validTabs;
    }

    public int getNumUniqueTabs() {
        return validTabs.size();
    }
    
}