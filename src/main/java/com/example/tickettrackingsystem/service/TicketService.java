package com.example.tickettrackingsystem.service;

import com.example.tickettrackingsystem.model.tracking.Ticket;

public interface TicketService {
    Ticket findTicketById(Long id);

    Ticket patchTicketById(Long id, Ticket ticket);
    //Bug save(Bug bug);

    Ticket patchTicketResolvedById(Long id, Ticket ticket);

    Ticket updateTicketById(Long id, Ticket ticket);

    Ticket updateTicketCommonById(Long id, Ticket ticket);
}
