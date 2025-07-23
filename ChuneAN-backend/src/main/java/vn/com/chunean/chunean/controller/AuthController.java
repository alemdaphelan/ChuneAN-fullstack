package vn.com.chunean.chunean.controller;
import jakarta.servlet.http.HttpServletResponse;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.chunean.chunean.entity.User;
import vn.com.chunean.chunean.request.LoginRequest;
import vn.com.chunean.chunean.request.SignInRequest;
import vn.com.chunean.chunean.services.UserService;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@RestController
@RequestMapping("api/users")
@CrossOrigin(origins = "http://localhost:5174")
public class AuthController {
    @Autowired
    UserService userService;
    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        String usernameOrEmail = loginRequest.getUsernameOrEmail();
        String password = loginRequest.getPassword();
        User user = userService.login(usernameOrEmail,password);
        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("incorrect username or password");
        }
        return ResponseEntity.status(HttpStatus.OK).body("login success");
    }
    @PostMapping("signin")
    public ResponseEntity<String> signIn (@RequestBody SignInRequest signInRequest) {
        String username = signInRequest.getUsername();
        String password = signInRequest.getPassword();
        String email = signInRequest.getEmail();
        String birthday = signInRequest.getBirthday();
        User existingUser = userService.getUserByEmailOrUsername(username,username);
        if(existingUser != null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("user is existed");
        }
        User user = userService.createUser(new User(username,password,email,birthday));
        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot create user");
        }
        return  ResponseEntity.status(HttpStatus.OK).body("signin success");
    }
}
