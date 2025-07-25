package vn.com.chunean.chunean.controller;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.chunean.chunean.entity.User;
import vn.com.chunean.chunean.request.LoginRequest;
import vn.com.chunean.chunean.request.SignInRequest;
import vn.com.chunean.chunean.services.JwtService;
import vn.com.chunean.chunean.services.UserService;

import java.time.Duration;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@RestController
@RequestMapping("api/users")

public class AuthController {
    UserService userService;
    JwtService jwtService;
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String usernameOrEmail = loginRequest.getUsernameOrEmail();
        String password = loginRequest.getPassword();
        User user = userService.login(usernameOrEmail,password);
        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("incorrect username or password");
        }
        String token = jwtService.generateJwt(user.getId());
        ResponseCookie cookie = ResponseCookie.from("jwt",token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .domain("localhost")
                .maxAge(Duration.ofHours(24))
                .sameSite("None")
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE,cookie.toString())
                .body(user);
    }

    @PostMapping("signin")
    public ResponseEntity<?> signIn (@RequestBody SignInRequest signInRequest) {
        String username = signInRequest.getUsername();
        String password = signInRequest.getPassword();
        String email = signInRequest.getEmail();
        String birthday = signInRequest.getBirthday();
        User existingUser = userService.getUserByEmailOrUsername(username,username);
        if(existingUser != null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("user is existed");
        }
        User user = userService.createUser(new User(username,password,email,birthday));

        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Cannot create user");
        }

        String token = jwtService.generateJwt(user.getId());
        ResponseCookie cookie = ResponseCookie.from("jwt",token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .domain("localhost")
                .maxAge(Duration.ofHours(24))
                .sameSite("None")
                .build();

        return  ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE,cookie.toString())
                .body(user);
    }
}
