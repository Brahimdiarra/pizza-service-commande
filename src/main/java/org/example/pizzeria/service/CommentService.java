package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.exception.ResourceNotFoundException;
import org.example.model.Comment;
import org.example.model.User;
import org.example.repository.CommentRepository;
import org.example.repository.PizzaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PizzaRepository pizzaRepository;
    private final UserService userService;

    public List<Comment> findByPizzaId(Long pizzaId) {
        return commentRepository.findByPizzaIdAndApprovedTrueOrderByCreatedAtDesc(pizzaId);
    }

    public Optional<Comment> findById(String id) {
        return commentRepository.findById(id);
    }

    public Comment addComment(Long pizzaId, Comment comment, String userEmail) {
        // Verify pizza exists
        if (!pizzaRepository.existsById(pizzaId)) {
            throw new ResourceNotFoundException("Pizza not found with id: " + pizzaId);
        }

        User user = userService.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));

        comment.setPizzaId(pizzaId);
        comment.setUserId(user.getId());
        comment.setUserName(user.getFullName());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setApproved(false); // Requiring moderation by default

        return commentRepository.save(comment);
    }

    public Comment approveComment(String id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));

        comment.setApproved(true);
        return commentRepository.save(comment);
    }

    public void deleteById(String id) {
        commentRepository.deleteById(id);
    }
}