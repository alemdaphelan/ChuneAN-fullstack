package vn.com.chunean.chunean.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import vn.com.chunean.chunean.entity.User;

import vn.com.chunean.chunean.repositories.UserRepository;
import lombok.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
@Service
public class UserService {
    final UserRepository userRepository;
    public List<User> getAllUser(){
        return userRepository.findAll();
    }
    public User getUserById(String id){
        return userRepository.findById(id).orElse(null);
    }
    public User getUserByEmailOrUsername(String username, String email){
        return userRepository.findByUsernameOrEmail(username,email);
    }
    public User login(String usernameOrEmail, String password){
        User user = this.getUserByEmailOrUsername(usernameOrEmail,usernameOrEmail);
        if(user == null) return null;
        if(!user.getPassword().equals(password)){
            return null;
        }
        return user;
    }
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return Collections.emptyList();
    }
    public User createUser(User user){
        return  userRepository.save(user);
    }
}
