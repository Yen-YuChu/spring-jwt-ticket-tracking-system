package com.example.tickettrackingsystem.model.tracking;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Content {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column
    private String summary;

    @NotNull
    @Column
    private String description;

    @NotNull
    @Column
    private Boolean resolved;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getResolved(){return  resolved; }

    public void setResolved(Boolean resolved) {this.resolved = resolved; }
}
