package com.canalbrewing.abcdatacollector.controller;

import com.canalbrewing.abcdatacollector.model.AppMode;
import com.canalbrewing.abcdatacollector.model.Relationship;
import com.canalbrewing.abcdatacollector.model.StatusMessage;
import com.canalbrewing.abcdatacollector.service.InitializeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("api/initial")
public class InitialController {
    private final InitializeService initializeService;

    @Autowired
    public InitialController(InitializeService initializeService) {
        this.initializeService = initializeService;
    }

    @GetMapping("/appMode")
    public ResponseEntity<AppMode> getAppMode() throws SQLException {
        return new ResponseEntity<>(initializeService.getAppMode(), HttpStatus.OK);
    }

    @PutMapping("/appMode")
    public ResponseEntity<AppMode> updateAppMode(@RequestBody AppMode appMode) throws SQLException {
        return new ResponseEntity<>(initializeService.updateAppMode(appMode), HttpStatus.OK);
    }

    @GetMapping("/relationships")
    public ResponseEntity<List<Relationship>> getRelationships() throws SQLException {
       return new ResponseEntity<>(initializeService.getRelationships(), HttpStatus.OK);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<StatusMessage> handleSQLException(SQLException ex) {
//        ex.printStackTrace();
        return new ResponseEntity<>(new StatusMessage(StatusMessage.ERROR, ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
