package vn.com.chunean.chunean.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, String> {
    Users findByUsernameOrEmail(String username,String email);
}
