package org.example.repository;

import org.example.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByPizzaIdAndApprovedTrueOrderByCreatedAtDesc(Long pizzaId);
    List<Comment> findByApprovedFalseOrderByCreatedAtAsc(); // Pour la modération
}