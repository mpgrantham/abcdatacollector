package com.canalbrewing.abcdatacollector.controller;

import com.canalbrewing.abcdatacollector.exceptions.AbcDataCollectorException;
import com.canalbrewing.abcdatacollector.model.Abc;
import com.canalbrewing.abcdatacollector.model.Observed;
import com.canalbrewing.abcdatacollector.model.StatusMessage;
import com.canalbrewing.abcdatacollector.service.AuthenticationService;
import com.canalbrewing.abcdatacollector.service.ObservedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("api/observed")
public class ObservedController {
    private final AuthenticationService authenticationService;
    private final ObservedService service;

    @Autowired
    public ObservedController(AuthenticationService authenticationService, ObservedService service) {
        this.authenticationService = authenticationService;
        this.service = service;
    }

    @GetMapping("/list/{userId}")
    public ResponseEntity<List<Observed>> getAllObserved(@PathVariable int userId)
            throws SQLException {
        return new ResponseEntity<>(service.getAllObserved(userId), HttpStatus.OK);
    }

    @GetMapping("/{observedId}")
    public ResponseEntity<Observed> getObserved(@PathVariable int observedId)
            throws SQLException {
        return new ResponseEntity<>(service.getObserved(observedId), HttpStatus.OK);
    }

    @GetMapping("/default")
    public ResponseEntity<Observed> getDefaultObserved() {
        return new ResponseEntity<>(service.getDefaultObserved(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Observed> insertObserved(@RequestBody Observed observed, @RequestHeader("Authorization") String sessionToken)
            throws SQLException, AbcDataCollectorException {
        authenticationService.authenticateUser(sessionToken);
        return new ResponseEntity<>(service.insertObserved(observed), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Observed> updateObserved(@RequestBody Observed observed, @RequestHeader("Authorization") String sessionToken)
            throws SQLException, AbcDataCollectorException {
        authenticationService.authenticateUser(sessionToken);
        return new ResponseEntity<>(service.updateObserved(observed), HttpStatus.OK);
    }

    @PostMapping("/{observedId}/abc")
    public ResponseEntity<Abc> insertObservedAbc(@PathVariable int observedId, @RequestBody Abc abc, @RequestHeader("Authorization") String sessionToken)
            throws SQLException, AbcDataCollectorException {
        authenticationService.authenticateUser(sessionToken);
        return new ResponseEntity<>(service.insertObservedAbc(observedId, abc), HttpStatus.OK);
    }

    @DeleteMapping("/{observedId}")
    public ResponseEntity<StatusMessage> deleteObserved(@PathVariable int observedId, @RequestHeader("Authorization") String sessionToken)
            throws SQLException, AbcDataCollectorException {
        authenticationService.authenticateUser(sessionToken);
        service.deleteObserved(observedId);
        return new ResponseEntity<>(new StatusMessage(StatusMessage.SUCCESS, "Observed Deleted"), HttpStatus.OK);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<StatusMessage> handleSQLException(SQLException ex) {
        return new ResponseEntity<>(new StatusMessage(StatusMessage.ERROR, ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AbcDataCollectorException.class)
    public ResponseEntity<StatusMessage> handleAbcDataCollectorException(AbcDataCollectorException ex) {
        return new ResponseEntity<>(new StatusMessage(StatusMessage.ERROR, ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
