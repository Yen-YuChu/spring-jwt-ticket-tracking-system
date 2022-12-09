package com.example.tickettrackingsystem.service;

import com.example.tickettrackingsystem.model.User;
import com.example.tickettrackingsystem.model.UserDto;

import java.util.List;

public interface UserService {
    User save(UserDto user);
    List<User> findAll();
    User findOne(String username);
}
