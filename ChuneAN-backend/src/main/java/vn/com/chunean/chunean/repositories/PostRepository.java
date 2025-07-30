package vn.com.chunean.chunean.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.chunean.chunean.entity.Post;
@Repository
public interface PostRepository extends JpaRepository<Post,String> {
}
