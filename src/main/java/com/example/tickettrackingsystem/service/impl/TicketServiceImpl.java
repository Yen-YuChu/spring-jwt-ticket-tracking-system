package com.example.tickettrackingsystem.service.impl;

import com.example.tickettrackingsystem.dao.TicketDao;
import com.example.tickettrackingsystem.model.tracking.Priority;
import com.example.tickettrackingsystem.model.tracking.Severity;
import com.example.tickettrackingsystem.model.tracking.Ticket;
import com.example.tickettrackingsystem.model.tracking.TicketType;
import com.example.tickettrackingsystem.service.IAuthenticationFacade;
import com.example.tickettrackingsystem.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service(value = "ticketService")
public class TicketServiceImpl implements TicketService {

    private static final String ROLE_PM = "ROLE_PM";
    private static final String ROLE_QA = "ROLE_QA";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    private static final String ROLE_RD = "ROLE_RD";

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

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

        return getCommonUpdatedTicket(originalTicket, ticket);
    }

    private Ticket getCommonUpdatedTicket(Ticket originalTicket, Ticket ticket)
    {
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

    public Ticket createTicketByRole(Ticket ticket) {
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(ROLE_PM))) {
            ticket.setTicketType(TicketType.FEATURE_REQUEST);
        } else if (userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(ROLE_QA))) {
            ticket.setTicketType(TicketType.TEST_CASE);
        }
        ticket.setResolved(false);

        return ticketDao.save(ticket);
    }

    public Ticket updateTicketByRole(Long id, Ticket ticket) {
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Ticket originalTicket = ticketDao.findTicketById(id);
        Boolean resolved = ticket.getResolved();

        if (userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(ROLE_PM) || a.getAuthority().equals(ROLE_RD))) {
            if (originalTicket.getTicketType().equals(TicketType.TEST_CASE))
            {
                throw new IllegalArgumentException("You don't have permission to update ticketType for TEST_CASE");
            }

        } else if (userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(ROLE_QA))) {
            if (originalTicket.getTicketType().equals(TicketType.FEATURE_REQUEST))
            {
                throw new IllegalArgumentException("You don't have permission to update ticketType for FEATURE_REQUEST");
            }
            if (resolved != null) {
                originalTicket.setResolved(resolved);
            }
        }

        if (userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(ROLE_PM) || a.getAuthority().equals(ROLE_QA) || a.getAuthority().equals(ROLE_ADMIN))) {
            getCommonUpdatedTicket(originalTicket, ticket);
        }

        if (userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(ROLE_RD))) {
            if (originalTicket.getTicketType().equals(TicketType.FEATURE_REQUEST)) {
                if (resolved != null) {
                    originalTicket.setResolved(resolved);
                }
            }
        }

        if (userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(ROLE_ADMIN))) {
            TicketType ticketType = ticket.getTicketType();
            if (ticketType != null) {
                originalTicket.setTicketType(ticketType);
            }
            if (resolved != null) {
                originalTicket.setResolved(resolved);
            }

        }
        return ticketDao.save(originalTicket);

    }

    public void deleteTicketById(Long id) {
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Ticket originalTicket = ticketDao.findTicketById(id);

        if (userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(ROLE_ADMIN) || (a.getAuthority().equals(ROLE_QA) && originalTicket.getTicketType().equals(TicketType.TEST_CASE)) )) {
            ticketDao.deleteById(id);
        } else {
            throw new IllegalArgumentException("You don't have permission to delete ticket");
        }
    }

    public Map<String, Object> getPageableResultByPageAndSize(Integer page, Integer size) {
        Pageable paging = PageRequest.of(page, size);

        Page<Ticket> pageTickets;
        pageTickets = ticketDao.findAll(paging);

        List<Ticket> tickets = pageTickets.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("tickets", tickets);
        response.put("currentPage", pageTickets.getNumber());
        response.put("totalItems", pageTickets.getTotalElements());
        response.put("totalPages", pageTickets.getTotalPages());

        return response;
    }

}
