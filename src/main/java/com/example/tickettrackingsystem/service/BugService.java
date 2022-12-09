package com.example.tickettrackingsystem.service;

import com.example.tickettrackingsystem.model.tracking.Bug;

public interface BugService {
    Bug findBugById(Long id);

    Bug patchBugById(Long id, Bug bug);
    //Bug save(Bug bug);

    Bug updateBugById(Long id, Bug bug);
}
