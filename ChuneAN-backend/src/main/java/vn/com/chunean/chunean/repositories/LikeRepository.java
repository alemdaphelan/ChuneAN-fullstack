package vn.com.chunean.chunean.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.chunean.chunean.entity.Like;
import vn.com.chunean.chunean.entity.Post;
import vn.com.chunean.chunean.entity.User;

@Repository
public interface LikeRepository extends JpaRepository<Like,String> {
    void deleteByUserAndPost(User user, Post post);

    long countByPost(Post post);

}
