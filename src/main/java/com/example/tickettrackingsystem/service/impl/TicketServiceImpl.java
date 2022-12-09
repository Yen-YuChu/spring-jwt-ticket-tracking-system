package com.example.tickettrackingsystem.service.impl;

import com.example.tickettrackingsystem.dao.TicketDao;
import com.example.tickettrackingsystem.model.tracking.Priority;
import com.example.tickettrackingsystem.model.tracking.Severity;
import com.example.tickettrackingsystem.model.tracking.Ticket;
import com.example.tickettrackingsystem.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "ticketService")
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketDao ticketDao;

    @Override
    public Ticket findTicketById(Long id) {
        Ticket ticket = ticketDao.findTicketById(id);
        return ticket;
    }

    public Ticket patchTicketById(Long id, Ticket ticket)
    {
        Ticket originalTicket = getCommonUpdatedTicket(id, ticket);
        return ticketDao.save(originalTicket);
    }
    public Ticket patchTicketResolvedById(Long id, Ticket ticket) {
        Ticket originalTicket = ticketDao.findTicketById(id);

        Boolean resolved = ticket.getResolved();
        if (resolved != null) {
            originalTicket.setResolved(resolved);
        }
        return ticketDao.save(originalTicket);
    }

    public Ticket updateTicketById(Long id, Ticket ticket)
    {
        Ticket originalTicket = getCommonUpdatedTicket(id, ticket);
        Boolean resolved = ticket.getResolved();
        if (resolved != null)
        {
            originalTicket.setResolved(resolved);
        }
        return ticketDao.save(originalTicket);
    }

    private Ticket getCommonUpdatedTicket(Long id, Ticket ticket)
    {
        Ticket originalTicket = ticketDao.findTicketById(id);

        String description = ticket.getDescription();
        String summary = ticket.getSummary();
        Priority priority = ticket.getPriority();
        Severity severity = ticket.getSeverity();
        if (priority != null) {
            originalTicket.setPriority(priority);
        }
        if (severity != null) {
            originalTicket.setSeverity(severity);
        }
        if (severity != null) {
            originalTicket.setSeverity(severity);
        }
        if (description != null) {
            originalTicket.setDescription(description);
        }
        if (summary != null) {
            originalTicket.setSummary(summary);
        }
        return originalTicket;
    }

    public Ticket updateTicketCommonById(Long id, Ticket ticket) {
        Ticket originalTicket = getCommonUpdatedTicket(id, ticket);

        return ticketDao.save(originalTicket);
    }

}
