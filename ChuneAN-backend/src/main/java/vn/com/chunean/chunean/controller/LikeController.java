package vn.com.chunean.chunean.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.com.chunean.chunean.dto.request.LikeRequest;
import vn.com.chunean.chunean.dto.response.LikeResponse;
import vn.com.chunean.chunean.entity.User;
import vn.com.chunean.chunean.exception.UnauthorizedException;
import vn.com.chunean.chunean.services.JwtService;
import vn.com.chunean.chunean.services.LikeService;

@RestController
@RequestMapping("/api/users/posts")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;
    private final JwtService jwtService;

    public User getUserByJwt(String jwt) {
        if(jwt == null || !jwtService.validateJwt(jwt)){
            throw new UnauthorizedException("Unauthorized");
        }
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @PostMapping("/like/{id}")
    public ResponseEntity<LikeResponse> likePost(@CookieValue(name="jwt") String jwt,@PathVariable(name = "id") String postId, @RequestBody LikeRequest request) {
        User user = getUserByJwt(jwt);
        request.setUserId(user.getId());
        request.setPostId(postId);
        LikeResponse response = likeService.likePost(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/unlike/{id}")
    public ResponseEntity<String> unlikePost(@CookieValue(name="jwt") String jwt,@PathVariable(name="id") String postId,@RequestBody LikeRequest request) {
        User user =  getUserByJwt(jwt);
        request.setUserId(user.getId());
        request.setPostId(postId);
        likeService.unlikePost(request);
        return ResponseEntity.ok("Unlike success");
    }

    @GetMapping("/likes/{id}")
    public ResponseEntity<Long> countLikes(@PathVariable (name="id") String postId) {
        long  count = likeService.countByPost(postId);
        return ResponseEntity.ok(count);
    }
}

