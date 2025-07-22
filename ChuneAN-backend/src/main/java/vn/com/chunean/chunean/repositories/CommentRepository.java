package vn.com.chunean.chunean.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.chunean.chunean.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, String> {
}
