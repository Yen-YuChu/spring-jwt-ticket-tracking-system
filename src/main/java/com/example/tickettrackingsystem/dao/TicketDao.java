package com.example.tickettrackingsystem.dao;

import com.example.tickettrackingsystem.model.tracking.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketDao extends JpaRepository<Ticket, Long> {
    Ticket findTicketById(Long id);

}