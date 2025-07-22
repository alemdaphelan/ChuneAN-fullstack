package vn.com.chunean.chunean.services;

import org.springframework.stereotype.Service;
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
    public List<Users> getAllUser(){
        return userRepository.findAll();
    }
    public Users getUserByEmailOrUsername(String username, String email){
        return userRepository.findByUsernameOrEmail(username,email);
    }
    public Users login(String usernameOrEmail, String password){
        Users user = this.getUserByEmailOrUsername(usernameOrEmail,usernameOrEmail);
        if(user == null) return null;
        if(!user.getPassword().equals(password)){
            return null;
        }
        return user;
    }
    public Users createUser(Users user){
        return  userRepository.save(user);
    }
}
