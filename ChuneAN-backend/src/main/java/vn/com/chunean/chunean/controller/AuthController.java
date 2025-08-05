package vn.com.chunean.chunean.controller;
import lombok.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.chunean.chunean.entity.User;
import vn.com.chunean.chunean.exception.BadRequestException;
import vn.com.chunean.chunean.exception.ConflictException;
import vn.com.chunean.chunean.dto.request.LoginRequest;
import vn.com.chunean.chunean.dto.request.SignInRequest;
import vn.com.chunean.chunean.services.AuthService;
import vn.com.chunean.chunean.services.JwtService;

import java.time.Duration;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class AuthController {

    final AuthService authService;
    final JwtService jwtService;

    private ResponseCookie buildCookie(String id) {
        String token = jwtService.generateJwt(id);
        return ResponseCookie.from("jwt", token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .domain("localhost")
                .maxAge(Duration.ofHours(24))
                .sameSite("Lax")
                .build();
    }

    private ResponseCookie killCookie() {
        return ResponseCookie.from("jwt","")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .domain("localhost")
                .maxAge(0)
                .sameSite("Lax")
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest) {
        String usernameOrEmail = loginRequest.getUsernameOrEmail();
        String password = loginRequest.getPassword();
        if(usernameOrEmail == null || usernameOrEmail.isBlank()) {
            throw new BadRequestException("Username or email is required");
        }
        if(password == null || password.isBlank()) {
            throw new BadRequestException("Password is required");
        }
        User user = authService.login(usernameOrEmail, password);
        ResponseCookie cookie = buildCookie(user.getId());

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(user);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest signInRequest) {
        String username = signInRequest.getUsername();
        String password = signInRequest.getPassword();
        String email = signInRequest.getEmail();
        String birthday = signInRequest.getBirthday();

        if(username == null || username.isBlank()){
            throw new BadRequestException("username is required");
        }
        if(password == null || password.isBlank()){
            throw new BadRequestException("password is required");
        }
        if(email == null || email.isBlank()){
            throw new BadRequestException("email is required");
        }
        if(birthday == null || birthday.isBlank()){
            throw new BadRequestException("birthday is required");
        }

        User existingUser = authService.getUserByEmailOrUsername(username, username);
        if (existingUser != null) {
            throw new ConflictException("User already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setBirth(birthday);
        User createdUser = authService.createUser(user);

        if (createdUser == null) {
            throw new RuntimeException();
        }
        ResponseCookie cookie = buildCookie(user.getId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(user);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        ResponseCookie cookie = killCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(null);
    }
}