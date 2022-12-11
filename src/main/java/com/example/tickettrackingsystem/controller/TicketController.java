package com.example.tickettrackingsystem.controller;

import com.example.tickettrackingsystem.dao.TicketDao;
import com.example.tickettrackingsystem.model.tracking.Ticket;
import com.example.tickettrackingsystem.service.TicketService;
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
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketDao ticketDao;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/ticket")
    @Operation(summary = "List Tickets", description = "List Tickets support pageable")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Map<String, Object>> getAllTicketsPageable(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "3") int size) {
        try {
            Map<String, Object> response = ticketService.getPageableResultByPageAndSize(page, size);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('PM') || hasRole('QA') || hasRole('ADMIN')")
    @PostMapping(value="/ticket")
    @Operation(summary = "Create Ticket", description = "Create Ticket")
    @SecurityRequirement(name = "Bearer Authentication")
    public Object createTicket(@RequestBody Ticket ticket) {
        try {
            ticket = ticketService.createTicketByRole(ticket);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ticket, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/ticket/{id}")
    @Operation(summary = "Get Ticket", description = "Get Ticket")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> getTicketById(@PathVariable Long id) {
        if (!ticketDao.existsById(id)) {
            return new ResponseEntity<>("Not found Ticket with id: " + id, HttpStatus.NOT_FOUND);
        }
        Ticket ticket = ticketDao.findTicketById(id);
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('QA')")
    @DeleteMapping("/ticket/{id}")
    @Operation(summary = "Delete Ticket", description = "Delete Ticket")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> deleteTicketById(@PathVariable Long id) {
        try {
            if (!ticketDao.existsById(id)) {
                return new ResponseEntity<>(HttpStatus.GONE);
            }
            ticketService.deleteTicketById(id);
        } catch (IllegalArgumentException iae) {
            return new ResponseEntity<>("You don't have permission to delete ticket for id:" + id, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('PM') || hasRole('QA') || hasRole('ADMIN') || hasRole('RD')")
    @PutMapping(value="/ticket/{id}")
    @Operation(summary = "Update Ticket", description = "Update Ticket")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> updateTicketById(@RequestBody Ticket ticket, @PathVariable Long id){
        Ticket updatedTicket;
        try {
            updatedTicket = ticketService.updateTicketByRole(id, ticket);
        } catch (IllegalArgumentException iae) {
            return new ResponseEntity<>("You don't have permission to update ticket for id:" + id, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(updatedTicket, HttpStatus.OK);
    }

}
