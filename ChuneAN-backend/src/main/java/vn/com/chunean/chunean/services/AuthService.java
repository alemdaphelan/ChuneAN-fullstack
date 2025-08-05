package vn.com.chunean.chunean.services;

import org.springframework.stereotype.Service;


import vn.com.chunean.chunean.entity.User;

import vn.com.chunean.chunean.exception.ResourceNotFoundException;
import vn.com.chunean.chunean.exception.UnauthorizedException;

import vn.com.chunean.chunean.repositories.UserRepository;
import lombok.*;


@RequiredArgsConstructor
@Getter
@Setter
@Service
public class AuthService {

    private final UserRepository userRepository;

    public User getUserByEmailOrUsername(String username, String email){
        return userRepository.findByUsernameOrEmail(username,email);
    }
    public User login(String usernameOrEmail, String password){
        User user = this.getUserByEmailOrUsername(usernameOrEmail,usernameOrEmail);
        if(user == null || !user.getPassword().equals(password)){
            throw new UnauthorizedException("Invalid username or password");
        }
        return user;
    }
    public User getUserById(String id){
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            throw new ResourceNotFoundException("User not found");
        }
        return user;
    }
    public User createUser(User user){
        return userRepository.save(user);
    }
}
