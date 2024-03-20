package com.canalbrewing.abcdatacollector.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Relationship {
    private int id;
    private String name;

    @Override
    public String toString() {
        return "Relationship [id=" + id + ", name=" + name + "]";
    }
}
