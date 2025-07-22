package vn.com.chunean.chunean.controller;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.chunean.chunean.request.LoginRequest;
import vn.com.chunean.chunean.request.SignInRequest;
import vn.com.chunean.chunean.services.UserService;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@RestController
@RequestMapping("api/users")
public class AuthController {
    UserService userService;
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String usernameOrEmail = loginRequest.getUsernameOrEmail();
        String password = loginRequest.getPassword();
        Users user = userService.login(usernameOrEmail,password);
        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("incorrect username or password");
        }
        return ResponseEntity.status(HttpStatus.OK).body("login success");
    }
    @PostMapping("/signin")
    public ResponseEntity<String> signIn (@RequestBody SignInRequest signInRequest) {
        String username = signInRequest.getUsername();
        String password = signInRequest.getPassword();
        String email = signInRequest.getEmail();
        String birthday = signInRequest.getBirthday();
        Users existingUser = userService.getUserByEmailOrUsername(username,username);
        if(existingUser != null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("user is existed");
        }
        Users user = userService.createUser(new Users(username,password,email,birthday));
        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot create user");
        }
        return  ResponseEntity.status(HttpStatus.OK).body("signin success");
    }
}
