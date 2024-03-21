package com.canalbrewing.abcdatacollector.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Incident {
    private int id;
    private Long incidentDate;
    private int observedId;
    private int userId;
    private Abc location;
    private int duration;
    private String intensity;
    private String description;

    private List<Abc> antecedents = new ArrayList<>();
    private List<Abc> behaviors = new ArrayList<>();
    private List<Abc> consequences = new ArrayList<>();

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
                location = abc;
                break;
            default:
        }
    }

    @Override
    public String toString() {
        return "Incident [id=" + id
                + ", incidentDate=" + incidentDate
                + ", observedId=" + observedId
                + ", userId=" + userId
                + ", duration=" + duration
                + ", intensity=" + intensity
                + ", description=" + description
                + "]";
    }
}
