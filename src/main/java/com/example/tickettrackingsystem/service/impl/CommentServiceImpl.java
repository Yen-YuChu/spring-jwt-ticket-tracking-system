package com.example.tickettrackingsystem.service.impl;

import com.example.tickettrackingsystem.dao.CommentDao;
import com.example.tickettrackingsystem.dao.TicketDao;
import com.example.tickettrackingsystem.model.tracking.Comment;
import com.example.tickettrackingsystem.service.CommentService;
import com.example.tickettrackingsystem.service.IAuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service(value = "commentService")
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private IAuthenticationFacade authenticationFacade;
    @Override
    public Comment findCommentById(Long id) {
        return commentDao.getOne(id);
    }

    public Optional<Comment> createCommentByTicket(Long ticketId, Comment comment) {
        String username = authenticationFacade.getUsername();
        return ticketDao.findById(ticketId).map(ticket -> {
            comment.setTicket(ticket);
            comment.setUsername(username);
            return commentDao.save(comment);
        });
    }

    private boolean isSelf(String commentUsername) {
        String username = authenticationFacade.getUsername();
        return commentUsername.equals(username);
    }

    public Comment updateCommentById(Long id, Comment comment) {
        Comment originalComment = commentDao.getOne(id);

        if (isSelf(originalComment.getUsername())) {
            originalComment.setContent(comment.getContent());
        } else {
            throw new IllegalArgumentException("You don't have permission to update");
        }
        return commentDao.save(originalComment);
    }

    public void deleteCommentById(Long id) {
        Comment originalComment = commentDao.getOne(id);

        if (isSelf(originalComment.getUsername())) {
            commentDao.deleteById(id);
        } else {
            throw new IllegalArgumentException("You don't have permission to delete");
        }
    }

    public Map<String, Object> getPageableResultByPageAndSize(Integer page, Integer size) {
        Pageable paging = PageRequest.of(page, size);

        Page<Comment> pageComments;
        pageComments = commentDao.findAll(paging);

        List<Comment> comments = pageComments.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("comments", comments);
        response.put("currentPage", pageComments.getNumber());
        response.put("totalItems", pageComments.getTotalElements());
        response.put("totalPages", pageComments.getTotalPages());

        return response;
    }
}
