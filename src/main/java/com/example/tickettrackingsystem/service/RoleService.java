package com.example.tickettrackingsystem.service;

import com.example.tickettrackingsystem.model.Role;

public interface RoleService {
    Role findByName(String name);
}
