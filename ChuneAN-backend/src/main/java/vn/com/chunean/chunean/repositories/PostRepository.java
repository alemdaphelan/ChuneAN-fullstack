package vn.com.chunean.chunean.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.chunean.chunean.entity.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,String> {
}
