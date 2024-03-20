package com.canalbrewing.abcdatacollector.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Observed {
    private int id;
    private String observedName;
    private int userId;
    private List<Abc> antecedents = new ArrayList<>();
    private List<Abc> behaviors = new ArrayList<>();
    private List<Abc> consequences = new ArrayList<>();
    private List<Abc> locations = new ArrayList<>();

    public void addAbcValue(Abc abc) {
        switch (abc.getTypeCode()) {
            case Abc.ANTECEDENT:
                this.antecedents.add(abc);
                break;
            case Abc.BEHAVIOR:
                this.behaviors.add(abc);
                break;
            case Abc.CONSEQUENCE:
                this.consequences.add(abc);
                break;
            case Abc.LOCATION:
                this.locations.add(abc);
                break;
            default:
        }
    }

    public void resetDefaults() {
        antecedents.clear();
        behaviors.clear();
        consequences.clear();
        locations.clear();
    }

    @Override
    public String toString() {
        return "Observed [id=" + id + ", observedName=" + observedName + ", userId=" + userId + "]";
    }
}
