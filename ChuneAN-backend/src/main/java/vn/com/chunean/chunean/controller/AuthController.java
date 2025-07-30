package vn.com.chunean.chunean.controller;
import lombok.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.com.chunean.chunean.entity.User;
import vn.com.chunean.chunean.exception.UnauthorizedException;
import vn.com.chunean.chunean.dto.request.LoginRequest;
import vn.com.chunean.chunean.dto.request.SignInRequest;
import vn.com.chunean.chunean.services.JwtService;
import vn.com.chunean.chunean.services.UserService;

import java.time.Duration;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class AuthController {

    final UserService userService;
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

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest) {
        String usernameOrEmail = loginRequest.getUsernameOrEmail();
        String password = loginRequest.getPassword();
        User user = userService.login(usernameOrEmail, password);
        if (user == null) {
            throw new UnauthorizedException("Incorrect username or password");
        }
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

        User existingUser = userService.getUserByEmailOrUsername(username, username);
        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("user is existed");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setBirth(birthday);
        User createdUser = userService.createUser(user);

        if (createdUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Cannot create user");
        }
        ResponseCookie cookie = buildCookie(user.getId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(user);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMe (@CookieValue(name="jwt",required = false) String jwt) {
        if(jwt == null || !jwtService.validateJwt(jwt)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid jwt");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(user);
    }
}