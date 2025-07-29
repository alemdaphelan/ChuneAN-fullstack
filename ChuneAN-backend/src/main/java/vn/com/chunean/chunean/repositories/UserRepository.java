package vn.com.chunean.chunean.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import vn.com.chunean.chunean.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUsernameOrEmail(String username,String email);
}
