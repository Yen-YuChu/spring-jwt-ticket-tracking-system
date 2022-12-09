package com.example.tickettrackingsystem.model.tracking;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Ticket extends Content {
    /*@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;*/

    @Enumerated(EnumType.STRING)
    @Column(name="severity")
    private Severity severity;

    @Enumerated(EnumType.STRING)
    @Column(name="priority")
    private Priority priority;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name="ticket_type")
    private TicketType ticketType;

    /*@Column
    private Boolean resolved;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }*/

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    /*public Boolean getResolved() {
        return resolved;
    }

    public void setResolved(Boolean resolved) {
        this.resolved = resolved;
    }*/

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
