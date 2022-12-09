package com.example.tickettrackingsystem.dao;

import com.example.tickettrackingsystem.model.tracking.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketDao extends CrudRepository<Ticket, Long> {
    Ticket findTicketById(Long id);

}