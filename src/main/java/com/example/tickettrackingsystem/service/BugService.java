package com.example.tickettrackingsystem.service;

import com.example.tickettrackingsystem.model.tracking.Bug;

import java.util.Map;

public interface BugService {
    Bug patchBugById(Long id, Bug bug);

    Bug updateBugById(Long id, Bug bug);

    Map<String, Object> getPageableResultByPageAndSize(Integer page, Integer size);

    Bug updateBugByRole(Long id, Bug bug);

    Bug createBug(Bug bug);
}
