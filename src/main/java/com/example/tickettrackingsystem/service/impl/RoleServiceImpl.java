package com.example.tickettrackingsystem.service.impl;

import com.example.tickettrackingsystem.dao.RoleDao;
import com.example.tickettrackingsystem.model.Role;
import com.example.tickettrackingsystem.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public Role findByName(String name) {
        Role role = roleDao.findRoleByName(name);
        return role;
    }
}
