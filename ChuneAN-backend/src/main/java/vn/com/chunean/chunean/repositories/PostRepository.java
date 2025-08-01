package vn.com.chunean.chunean.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.com.chunean.chunean.entity.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,String> {
    @EntityGraph(attributePaths = {"user"})
    @Query("""
                select p from Post p
""")
    List<Post> findAllWithUser();

    @EntityGraph(attributePaths = {"user"})
    @Query("""
            select p from Post p
            where p.user.id in
            (
                select f.following.id from Following f
                where f.user.id=:userId
            )
            order by p.createdAt DESC
""")
    List<Post> getAllPostsFromFollowing(String userId);

    @EntityGraph(attributePaths = {"user"})
    @Query("""
                select p from Post p
                order by p.likeCount DESC
""")
    List<Post> getTrendingPosts();
    @EntityGraph(attributePaths = {"user"})
    @Query("""
                select p from Post p
                order by p.createdAt desc
""")
    List<Post> getNewestPost();
}
