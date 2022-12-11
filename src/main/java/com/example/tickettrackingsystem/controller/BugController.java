package com.example.tickettrackingsystem.controller;

import com.example.tickettrackingsystem.dao.BugDao;
import com.example.tickettrackingsystem.model.tracking.Bug;
import com.example.tickettrackingsystem.service.BugService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class BugController {

    @Autowired
    private BugService bugService;

    @Autowired
    private BugDao bugDao;

    @PreAuthorize("hasRole('QA') || hasRole('ADMIN')")
    @PostMapping(value="/bug")
    @Operation(summary = "Create Bug", description = "Create Bug")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> createBug(@RequestBody Bug bug){
        try {
            bug = bugService.createBug(bug);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(bug, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('QA') || hasRole('ADMIN')")
    @DeleteMapping(value = "/bug/{id}")
    @Operation(summary = "Delete Bug", description = "Delete Bug")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> deleteBugById(@PathVariable Long id) {
        if (!bugDao.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.GONE);
        }
        bugDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('QA') || hasRole('ADMIN') || hasRole('RD')")
    @PutMapping(value="/bug/{id}")
    @Operation(summary = "Update Bug", description = "Update Bug")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> updateBugById(@RequestBody Bug bug, @PathVariable Long id){
        if (!bugDao.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bugService.updateBugByRole(id, bug), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/bug")
    @Operation(summary = "List Bugs", description = "List Bugs supported pageable")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Map<String, Object>> getAllBugsPageable(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "3") int size) {
        try {
            Map<String, Object> response = bugService.getPageableResultByPageAndSize(page, size);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value="/bug/{id}")
    @Operation(summary = "Get Bug", description = "Get Bug")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> getBugById(@PathVariable Long id){
        if (!bugDao.existsById(id)) {
            return new ResponseEntity<>("Not found Bug with id: " + id, HttpStatus.NOT_FOUND);
        }
        Bug bug = bugDao.findBugById(id);
        return new ResponseEntity<>(bug, HttpStatus.OK);
    }


}
