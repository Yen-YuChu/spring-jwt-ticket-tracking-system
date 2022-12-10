package com.example.tickettrackingsystem.service;

import com.example.tickettrackingsystem.model.tracking.Ticket;

import java.util.Map;

public interface TicketService {
    Ticket findTicketById(Long id);

    Ticket patchTicketById(Long id, Ticket ticket);

    Ticket patchTicketResolvedById(Long id, Ticket ticket);

    Ticket updateTicketById(Long id, Ticket ticket);

    Ticket updateTicketCommonById(Long id, Ticket ticket);

    Ticket createTicketByRole(Ticket ticket);

    Ticket updateTicketByRole(Long id, Ticket ticket);

    void deleteTicketById(Long id);

    Map<String, Object> getPageableResultByPageAndSize(Integer page, Integer size);
}
