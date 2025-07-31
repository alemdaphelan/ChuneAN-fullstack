package vn.com.chunean.chunean.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.com.chunean.chunean.entity.User;
import vn.com.chunean.chunean.services.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/info")
    public ResponseEntity<?> getMe (@CookieValue(name="jwt",required = false) String jwt) {
        if(jwt==null || jwt.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User unauthenticated");
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(user);
    }
    @GetMapping("info/{id}")
    public ResponseEntity<?> getUser(@CookieValue(name="jwt",required = false) String jwt,@PathVariable("id") String id) {
        if(jwt==null || jwt.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User unauthenticated");
        }
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

}
