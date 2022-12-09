package com.example.tickettrackingsystem.service.impl;

import com.example.tickettrackingsystem.dao.BugDao;
import com.example.tickettrackingsystem.model.tracking.Bug;
import com.example.tickettrackingsystem.service.BugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "bugService")
public class BugServiceImpl implements BugService {

    @Autowired
    private BugDao bugDao;

    @Override
    public Bug findBugById(Long id) {
        Bug bug = bugDao.findBugById(id);
        return bug;
    }
    public Bug patchBugById(Long id, Bug bug) {
        Bug originalBug = bugDao.findBugById(id);

        Boolean resolved = bug.getResolved();
        if (resolved != null) {
            originalBug.setResolved(resolved);
        }
        return bugDao.save(originalBug);
    }

    public Bug updateBugById(Long id, Bug bug) {
        Bug originalBug = bugDao.findBugById(id);

        String summary = bug.getSummary();
        String description = bug.getDescription();
        if (summary != null) {
            originalBug.setSummary(summary);
        }
        if (description != null) {
            originalBug.setDescription(description);
        }
        return bugDao.save(originalBug);
    }

}
