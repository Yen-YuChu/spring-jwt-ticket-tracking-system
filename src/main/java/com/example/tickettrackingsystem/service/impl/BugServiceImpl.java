package com.example.tickettrackingsystem.service.impl;

import com.example.tickettrackingsystem.dao.BugDao;
import com.example.tickettrackingsystem.model.tracking.Bug;
import com.example.tickettrackingsystem.service.BugService;
import com.example.tickettrackingsystem.service.IAuthenticationFacade;
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

@Service(value = "bugService")
public class BugServiceImpl implements BugService {

    private static final String ROLE_QA = "ROLE_QA";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    private static final String ROLE_RD = "ROLE_RD";
    @Autowired
    private IAuthenticationFacade authenticationFacade;
    @Autowired
    private BugDao bugDao;

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

        getUpdatedBug(originalBug, bug);

        return bugDao.save(originalBug);
    }

    private Bug getUpdatedBug(Bug originalBug, Bug bug) {
        String summary = bug.getSummary();
        String description = bug.getDescription();
        if (summary != null) {
            originalBug.setSummary(summary);
        }
        if (description != null) {
            originalBug.setDescription(description);
        }
        return originalBug;
    }

    public Bug createBug(Bug bug) {
        bug.setResolved(false);
        return bugDao.save(bug);
    }

    public Map<String, Object> getPageableResultByPageAndSize(Integer page, Integer size) {
        Pageable paging = PageRequest.of(page, size);

        Page<Bug> pageBugs;
        pageBugs = bugDao.findAll(paging);

        List<Bug> bugs = pageBugs.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("bugs", bugs);
        response.put("currentPage", pageBugs.getNumber());
        response.put("totalItems", pageBugs.getTotalElements());
        response.put("totalPages", pageBugs.getTotalPages());

        return response;
    }

    public Bug updateBugByRole(Long id, Bug bug) {
        Authentication authentication = authenticationFacade.getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Bug originalBug = bugDao.findBugById(id);

        if (userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(ROLE_QA) || a.getAuthority().equals(ROLE_ADMIN))) {
            getUpdatedBug(originalBug, bug);
        }
        if (userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(ROLE_RD) || a.getAuthority().equals(ROLE_ADMIN))) {
            Boolean resolved = bug.getResolved();
            if (resolved != null) {
                originalBug.setResolved(resolved);
            }
        }
        return bugDao.save(originalBug);

    }

}
