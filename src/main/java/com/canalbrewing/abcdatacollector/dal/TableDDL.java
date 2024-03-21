package com.canalbrewing.abcdatacollector.dal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableDDL {
    private String name;
    private String dropStatement;
    private String createStatement;
    private String[] initStatements = {};

    public TableDDL(String name, String dropStatement, String createStatement) {
        this.name = name;
        this.dropStatement = dropStatement;
        this.createStatement = createStatement;
    }

    public TableDDL(String name, String dropStatement, String createStatement, String[] initStatements) {
        this.name = name;
        this.dropStatement = dropStatement;
        this.createStatement = createStatement;
        this.initStatements = initStatements;
    }
}
