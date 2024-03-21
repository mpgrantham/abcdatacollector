package com.canalbrewing.abcdatacollector.controller;

import com.canalbrewing.abcdatacollector.common.AppConstants;
import com.canalbrewing.abcdatacollector.exceptions.AbcDataCollectorException;
import com.canalbrewing.abcdatacollector.model.Incident;
import com.canalbrewing.abcdatacollector.model.StatusMessage;
import com.canalbrewing.abcdatacollector.service.AuthenticationService;
import com.canalbrewing.abcdatacollector.service.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("api/incident")
public class IncidentController {
    private final AuthenticationService authenticationService;
    private final IncidentService service;

    @Autowired
    public IncidentController(AuthenticationService authenticationService, IncidentService service) {
        this.authenticationService = authenticationService;
        this.service = service;
    }

    @GetMapping("/intensities")
    public ResponseEntity<String[]> getIntensities() throws SQLException {
        return new ResponseEntity<>(AppConstants.INTENSITIES, HttpStatus.OK);
    }

    @GetMapping("/list/{observedId}")
    public ResponseEntity<List<Incident>> getIncidents(@PathVariable int observedId,
                                                       @RequestParam(value = "start", required = false) Long incidentStartDate,
                                                       @RequestHeader("Authorization") String sessionToken)
            throws SQLException, AbcDataCollectorException {
        authenticationService.authenticateUser(sessionToken);
        return new ResponseEntity<>(service.getIncidents(observedId, incidentStartDate), HttpStatus.OK);
    }

    @GetMapping("/{incidentId}")
    public ResponseEntity<Incident> getIncident(@PathVariable int incidentId,
                                                      @RequestHeader("Authorization") String sessionToken)
            throws SQLException, AbcDataCollectorException {
        authenticationService.authenticateUser(sessionToken);
        return new ResponseEntity<>(service.getIncident(incidentId), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Incident> insertIncident(@RequestBody Incident incident, @RequestHeader("Authorization") String sessionToken)
            throws SQLException, AbcDataCollectorException {
        authenticationService.authenticateUser(sessionToken);
        return new ResponseEntity<>(service.insertIncident(incident), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Incident> updateIncident(@RequestBody Incident incident, @RequestHeader("Authorization") String sessionToken)
            throws SQLException, AbcDataCollectorException {
        authenticationService.authenticateUser(sessionToken);
        return new ResponseEntity<>(service.updateIncident(incident), HttpStatus.OK);
    }

    @DeleteMapping("/{incidentId}")
    public ResponseEntity<StatusMessage> deleteIncident(@PathVariable int incidentId,
                                                @RequestHeader("Authorization") String sessionToken)
            throws SQLException, AbcDataCollectorException {
        authenticationService.authenticateUser(sessionToken);
        service.deleteIncident(incidentId);
        return new ResponseEntity<>(new StatusMessage(StatusMessage.SUCCESS, "Incident Deleted"), HttpStatus.OK);
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
