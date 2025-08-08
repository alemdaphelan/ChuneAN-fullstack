package vn.com.chunean.chunean.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.com.chunean.chunean.dto.request.FollowingRequest;
import vn.com.chunean.chunean.dto.response.FollowingResponse;
import vn.com.chunean.chunean.entity.User;
import vn.com.chunean.chunean.exception.UnauthorizedException;
import vn.com.chunean.chunean.services.FollowingService;
import vn.com.chunean.chunean.services.JwtService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")

public class FollowingController {
    private final FollowingService followingService;
    private final JwtService jwtService;

    public String getUserId(String jwt) {
        if(jwt == null || !jwtService.validateJwt(jwt)){
            throw new UnauthorizedException("Unauthorized");
        }
        User user = (User)  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }

    @PostMapping("/follow")
    public ResponseEntity<?> follow(@CookieValue(name="jwt") String jwt, @RequestBody FollowingRequest followingRequest){
        String userId = getUserId(jwt);
        followingRequest.setUserId(userId);
        followingService.createFollowing(followingRequest);
        return ResponseEntity.ok().body("Followed successfully");
    }

    @DeleteMapping("/unfollow")
    public ResponseEntity<?> unfollow(@CookieValue(name="jwt") String jwt, @RequestBody FollowingRequest followingRequest){
        String userId = getUserId(jwt);
        followingRequest.setUserId(userId);
        followingService.deleteFollowing(followingRequest);
        return ResponseEntity.ok().body("Unfollowed successfully");
    }

    @GetMapping("/following")
    public ResponseEntity<?> getFollowing(@CookieValue(name="jwt") String jwt){
        String userId = getUserId(jwt);
        List<FollowingResponse>followingList = followingService.getAllFollowing(userId);
        return  ResponseEntity.ok().body(followingList);
    }
}
