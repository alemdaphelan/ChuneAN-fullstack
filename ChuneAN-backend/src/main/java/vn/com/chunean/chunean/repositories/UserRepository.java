package vn.com.chunean.chunean.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.com.chunean.chunean.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsernameOrEmail(String username,String email);
}
