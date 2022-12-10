package com.example.tickettrackingsystem.service;

import com.example.tickettrackingsystem.model.tracking.Comment;

import java.util.Map;
import java.util.Optional;

public interface CommentService {
    Comment findCommentById(Long id);

    Map<String, Object> getPageableResultByPageAndSize(Integer page, Integer size);

    Optional<Comment> createCommentByTicket(Long id, Comment comment);

    Comment updateCommentById(Long id, Comment comment);

    void deleteCommentById(Long id);
}
