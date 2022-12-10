package com.example.tickettrackingsystem.controller;

import java.util.*;

import com.example.tickettrackingsystem.dao.CommentDao;
import com.example.tickettrackingsystem.dao.TicketDao;
import com.example.tickettrackingsystem.model.tracking.Comment;
import com.example.tickettrackingsystem.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private CommentService commentService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/ticket/{id}/comments")
    public ResponseEntity<Object> getAllCommentsByTicketId(@PathVariable(value = "id") Long ticketId) {
        if (!ticketDao.existsById(ticketId)) {
            return new ResponseEntity<>("Not found Ticket with id: " + ticketId, HttpStatus.NOT_FOUND);
        }

        List<Comment> comments = commentDao.findByTicketId(ticketId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/comment/{id}")
    public ResponseEntity<Object> getCommentByCommentId(@PathVariable(value = "id") Long commentId) {
        Optional<Comment> comment = commentDao.findById(commentId);
        if (!comment.isPresent()) {
            return new ResponseEntity<>("Not found Comment with id: " + commentId, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/ticket/{id}/comment")
    public ResponseEntity<Object> createComment(@PathVariable(value = "id") Long ticketId, @RequestBody Comment commentRequest) {
        if (!ticketDao.existsById(ticketId)) {
            return new ResponseEntity<>("Not found Ticket with id: " + ticketId, HttpStatus.NOT_FOUND);
        }

        Optional<Comment> comment = commentService.createCommentByTicket(ticketId, commentRequest);

        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/comment/{id}")
    public ResponseEntity<Object> updateComment(@PathVariable("id") long id, @RequestBody Comment commentRequest) {
        if (!commentDao.existsById(id)){
            return new ResponseEntity<>("Not found Comment with id: " + id, HttpStatus.NOT_FOUND);
        }
        Comment comment;
        try {
            comment = commentService.updateCommentById(id, commentRequest);
        } catch (IllegalArgumentException iae) {
            return new ResponseEntity<>("You don't have permission to update Comment with id: " + id, HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<Object> deleteComment(@PathVariable("id") long id) {
        if (!commentDao.existsById(id)){
            return new ResponseEntity<>(HttpStatus.GONE);
        }
        try {
            commentService.deleteCommentById(id);
        } catch (IllegalArgumentException iae) {
            return new ResponseEntity<>("You don't have permission to delete Comment with id: " + id, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/ticket/{id}/comment")
    public ResponseEntity<Object> deleteAllCommentsOfTicket(@PathVariable(value = "id") Long ticketId) {
        try {
            commentDao.deleteByTicketId(ticketId);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            return new ResponseEntity<>("Not found Ticket with id: " + ticketId, HttpStatus.GONE);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
