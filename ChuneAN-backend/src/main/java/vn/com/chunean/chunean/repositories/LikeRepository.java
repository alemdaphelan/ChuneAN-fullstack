package vn.com.chunean.chunean.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.chunean.chunean.entity.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, String> {
}
