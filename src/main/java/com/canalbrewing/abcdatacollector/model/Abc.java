package com.canalbrewing.abcdatacollector.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Abc {
    public static final String ANTECEDENT = "A";
    public static final String BEHAVIOR = "B";
    public static final String CONSEQUENCE = "C";
    public static final String LOCATION = "L";

    private int id;
    private String typeCode;
    private String value;
    private int selected;
    private Boolean selectedFlag = false;
    private String activeFl;

    @Override
    public String toString() {
        return "Abc [activeFl=" + activeFl + ", selected=" + selected + ", selectedFlag=" + selectedFlag + ", typeCode="
                + typeCode + ", value=" + value + ", id=" + id + "]";
    }
}
