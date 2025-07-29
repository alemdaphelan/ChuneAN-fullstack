package vn.com.chunean.chunean.controller;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.com.chunean.chunean.entity.User;
import vn.com.chunean.chunean.exception.BadRequestException;
import vn.com.chunean.chunean.exception.UnauthorizedException;
import vn.com.chunean.chunean.dto.request.LoginRequest;
import vn.com.chunean.chunean.request.SignInRequest;
import vn.com.chunean.chunean.services.UserService;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@RestController
@RequestMapping("/api/users")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        String usernameOrEmail = loginRequest.getUsernameOrEmail();
        String password = loginRequest.getPassword();

        User user = userService.login(usernameOrEmail, password);

        if (user == null) {
            throw new UnauthorizedException("Incorrect username or password");
        }

        return "Login success";
    }

    @PostMapping("/signin")
    public String signIn(@RequestBody SignInRequest signInRequest) {
        String username = signInRequest.getUsername();
        String password = signInRequest.getPassword();
        String email = signInRequest.getEmail();
        String birthday = signInRequest.getBirthday();

        User existingUser = userService.getUserByEmailOrUsername(username, username);
        if (existingUser != null) {
            throw new UnauthorizedException("User already exists");
        }

        User createdUser = userService.createUser(new User(username, password, email, birthday));
        if (createdUser == null) {
            throw new BadRequestException("Cannot create user");
        }

        return "Sign in success";
    }
}
