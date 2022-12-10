package com.example.tickettrackingsystem.dao;


import java.util.List;

import javax.transaction.Transactional;

import com.example.tickettrackingsystem.model.tracking.Comment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentDao extends JpaRepository<Comment, Long> {
    List<Comment> findByTicketId(Long postId);


    @Transactional
    void deleteByTicketId(long ticketId);
}
