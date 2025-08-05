package vn.com.chunean.chunean.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.com.chunean.chunean.entity.Following;

import java.util.List;

@Repository
public interface FollowingRepository extends JpaRepository<Following,String> {
    @Modifying
    @Transactional
    @Query("""
        delete from Following f where f.user.id = :userId and f.following.id = :followingId
""")
    void deleteFollowById(String userId,String followingId);

    @EntityGraph(attributePaths = {"user", "following"})
    @Query("""
        select f from Following f where f.user.id = :userId
""")
    List<Following> findFollowingByUserId(String userId);

    @EntityGraph(attributePaths = {"user", "following"})
    @Query("""
        select f from Following f where f.following.id = :userId order by f.user.username
""")
    List<Following> findFollowerByUserId(String userId);

}
