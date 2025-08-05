package vn.com.chunean.chunean.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.com.chunean.chunean.dto.response.UserResponse;
import vn.com.chunean.chunean.entity.User;
import vn.com.chunean.chunean.exception.UnauthorizedException;
import vn.com.chunean.chunean.services.JwtService;
import vn.com.chunean.chunean.services.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    public User getUserByJwt(String jwt) {
        if(jwt == null || !jwtService.validateJwt(jwt)){
            throw new UnauthorizedException("Unauthorized");
        }
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping("/info")
    public ResponseEntity<?> getMe (@CookieValue(name="jwt") String jwt) {
        User user = getUserByJwt(jwt);
        UserResponse userResponse = userService.getUserById(user.getId());
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("info/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") String id) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

}
