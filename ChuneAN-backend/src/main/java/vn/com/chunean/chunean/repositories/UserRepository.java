package vn.com.chunean.chunean.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.chunean.chunean.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUsernameOrEmail(String username,String email);

    @Query("""
            select u from User u
            where u.username like concat('%',:username,'%')
            order by u.createdAt desc
""")
    List<User> searchUserByUsername(@Param("username") String username);
}
