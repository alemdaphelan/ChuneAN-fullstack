package vn.com.chunean.chunean.services;

import org.springframework.stereotype.Service;
import vn.com.chunean.chunean.entity.User;

import vn.com.chunean.chunean.repositories.UserRepository;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Service
public class UserService {
    UserRepository userRepository;
    public List<User> getAllUser(){
        return userRepository.findAll();
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
    public User createUser(User user){
        return  userRepository.save(user);
    }
}
