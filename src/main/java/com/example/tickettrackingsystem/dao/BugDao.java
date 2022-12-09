package com.example.tickettrackingsystem.dao;

import com.example.tickettrackingsystem.model.tracking.Bug;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BugDao extends CrudRepository<Bug, Long> {
    Bug findBugById(Long id);

}