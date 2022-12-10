package com.example.tickettrackingsystem.model.tracking;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Ticket extends Content {
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name="severity")
    private Severity severity;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name="priority")
    private Priority priority;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name="ticket_type")
    private TicketType ticketType;

    @OneToMany(mappedBy="ticket", cascade = CascadeType.REMOVE)
    private List<Comment> comments;
    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }
}
