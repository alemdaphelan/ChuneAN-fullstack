package vn.com.chunean.chunean.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import vn.com.chunean.chunean.dto.request.SearchRequest;
import vn.com.chunean.chunean.dto.response.UserResponse;
import vn.com.chunean.chunean.entity.User;
import vn.com.chunean.chunean.exception.UnauthorizedException;
import vn.com.chunean.chunean.services.JwtService;
import vn.com.chunean.chunean.services.UserService;

import java.io.IOException;
import java.util.List;

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

    @PostMapping("/find")
    public ResponseEntity<?> findUser(@RequestBody SearchRequest searchRequest){
        String searchValue = searchRequest.getSearch();
        List<UserResponse> userResponseList = userService.findUserByUsername(searchValue);
        return ResponseEntity.ok(userResponseList);
    }

    @GetMapping("/avatar")
    public ResponseEntity<byte[]> getMyAvatar(@CookieValue(name="jwt") String jwt) throws IOException {
        User user = getUserByJwt(jwt);
        try{
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<byte[]> response = restTemplate.getForEntity(user.getAvatarUrl(), byte[].class);
            if(response.getStatusCode().is2xxSuccessful()){
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(response.getBody());
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/avatar/{id}")
    public ResponseEntity<byte[]> getUserAvatar(@PathVariable("id") String id) throws IOException {
        UserResponse user = userService.getUserById(id);
        try{
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<byte[]> response = restTemplate.getForEntity(user.getAvatarUrl(), byte[].class);
            if(response.getStatusCode().is2xxSuccessful()){
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(response.getBody());
            }
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
        return  ResponseEntity.notFound().build();
    }
}
