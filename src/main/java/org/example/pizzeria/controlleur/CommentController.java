package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.Comment;
import org.example.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/pizzas/{pizzaId}/comments")
    public ResponseEntity<List<Comment>> getCommentsForPizza(@PathVariable Long pizzaId) {
        return ResponseEntity.ok(commentService.findByPizzaId(pizzaId));
    }

    @PostMapping("/pizzas/{pizzaId}/comments")
    public ResponseEntity<Comment> addComment(
            @PathVariable Long pizzaId,
            @Valid @RequestBody Comment comment,
            @AuthenticationPrincipal UserDetails userDetails) {
        return new ResponseEntity<>(
                commentService.addComment(pizzaId, comment, userDetails.getUsername()),
                HttpStatus.CREATED);
    }

    @PutMapping("/comments/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Comment> approveComment(@PathVariable String id) {
        return commentService.findById(id)
                .map(comment -> ResponseEntity.ok(commentService.approveComment(id)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable String id,
            @AuthenticationPrincipal UserDetails userDetails) {
        return commentService.findById(id)
                .map(comment -> {
                    // Check if the comment belongs to the current user or if the user is an admin
                    if (comment.getUserId().toString().equals(userDetails.getUsername()) ||
                            userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                        commentService.deleteById(id);
                        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                    }
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}