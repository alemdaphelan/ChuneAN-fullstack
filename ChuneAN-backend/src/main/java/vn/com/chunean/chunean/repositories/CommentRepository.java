package vn.com.chunean.chunean.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.chunean.chunean.entity.Comment;
import vn.com.chunean.chunean.entity.Post;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
    @EntityGraph(attributePaths = {"user"})
    List<Comment> findByPost(Post post);

    long countByPost(Post post);
}