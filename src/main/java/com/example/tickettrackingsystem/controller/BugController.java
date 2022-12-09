package com.example.tickettrackingsystem.controller;

import com.example.tickettrackingsystem.dao.BugDao;
import com.example.tickettrackingsystem.model.UserDto;
import com.example.tickettrackingsystem.model.tracking.Bug;
import com.example.tickettrackingsystem.service.BugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/bug")
public class BugController {

    @Autowired
    private BugService bugService;

    @Autowired
    private BugDao bugDao;

    @PreAuthorize("hasRole('QA') || hasRole('ADMIN')")
    @RequestMapping(value="/", method = RequestMethod.POST)
    @ResponseStatus( HttpStatus.CREATED )
    public Bug createBug(@RequestBody Bug bug){
        bug.setResolved(false);
        return bugDao.save(bug);
    }

    @PreAuthorize("hasRole('QA') || hasRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteBugById(@PathVariable Long id) {
        try {
            bugDao.deleteById(id);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            return new ResponseEntity<>("No Bug issue exist for id: " + id, HttpStatus.GONE);
        }
        return new ResponseEntity<>("Succeed", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('QA') || hasRole('ADMIN')")
    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public Bug updateBugById(@RequestBody Bug bug, @PathVariable Long id){
        return bugService.updateBugById(id, bug);
    }

    @PreAuthorize("hasRole('RD') || hasRole('ADMIN')")
    @RequestMapping(value="/{id}", method = RequestMethod.PATCH)
    public Bug patchBugById(@RequestBody Bug bug, @PathVariable Long id){
        return bugService.patchBugById(id, bug);
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value="/", method = RequestMethod.GET)
    public Iterable<Bug> listBug(){
        return bugDao.findAll();
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public Bug getBugById(@PathVariable Long id){
        return bugDao.findBugById(id);
    }



}
