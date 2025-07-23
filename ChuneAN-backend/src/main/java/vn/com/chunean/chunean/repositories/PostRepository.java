package vn.com.chunean.chunean.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.chunean.chunean.entity.Post;

public interface PostRepository extends JpaRepository<Post, String> {
}