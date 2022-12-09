package com.example.tickettrackingsystem.controller;

import com.example.tickettrackingsystem.dao.TicketDao;
import com.example.tickettrackingsystem.model.tracking.Ticket;
import com.example.tickettrackingsystem.model.tracking.TicketType;
import com.example.tickettrackingsystem.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketDao ticketDao;

    @PreAuthorize("hasRole('PM') || hasRole('ADMIN')")
    @RequestMapping(value="/feature", method = RequestMethod.POST)
    @ResponseStatus( HttpStatus.CREATED )
    public Object createFeatureTicket(@RequestBody Ticket ticket) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        System.out.println("username : " + username);
        try {
            ticket.setResolved(false);
            ticket.setTicketType(TicketType.FEATURE_REQUEST);
            ticket = ticketDao.save(ticket);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ticket;
    }

    @PreAuthorize("hasRole('QA') || hasRole('ADMIN')")
    @RequestMapping(value="/test", method = RequestMethod.POST)
    @ResponseStatus( HttpStatus.CREATED )
    public Object createTestTicket(@RequestBody Ticket ticket) {
        try {
            ticket.setResolved(false);
            ticket.setTicketType(TicketType.TEST_CASE);
            ticket = ticketDao.save(ticket);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ticket;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/feature/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteFeatureTicketById(@PathVariable Long id) {
        try {
            ticketDao.deleteById(id);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            return new ResponseEntity<>("No Ticket issue exist for id: " + id, HttpStatus.GONE);
        }
        return new ResponseEntity<>("Succeed", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('PM') || hasRole('ADMIN')")
    @RequestMapping(value="/feature/{id}", method = RequestMethod.PATCH)
    public Ticket patchFeatureTicketById(@RequestBody Ticket ticket, @PathVariable Long id){
        return ticketService.patchTicketById(id, ticket);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/feature/{id}", method = RequestMethod.PUT)
    public Ticket updateFeatureTicketById(@RequestBody Ticket ticket, @PathVariable Long id){
        return ticketService.updateTicketById(id, ticket);
    }

    @PreAuthorize("hasRole('QA') || hasRole('ADMIN')")
    @RequestMapping(value="/test/{id}", method = RequestMethod.PUT)
    public Ticket updateTestTicketById(@RequestBody Ticket ticket, @PathVariable Long id){
        return ticketService.updateTicketById(id, ticket);
    }

    @PreAuthorize("hasRole('RD') || hasRole('ADMIN')")
    @RequestMapping(value="/feature/{id}/resolved", method = RequestMethod.PATCH)
    public Ticket patchFeatureTicketResolvedById(@RequestBody Ticket ticket, @PathVariable Long id){
        return ticketService.patchTicketResolvedById(id, ticket);
    }
}
