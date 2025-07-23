package vn.com.chunean.chunean.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.chunean.chunean.entity.Like;

public interface LikeRepository extends JpaRepository<Like, String> {
}
