package vn.com.chunean.chunean.controller;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.com.chunean.chunean.dto.request.PostRequest;
import vn.com.chunean.chunean.dto.response.PostResponse;
import vn.com.chunean.chunean.entity.User;
import vn.com.chunean.chunean.exception.UnauthorizedException;
import vn.com.chunean.chunean.services.JwtService;
import vn.com.chunean.chunean.services.PostService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/users/posts")
public class PostController {
    final private JwtService jwtService;
    final private PostService postService;

    private String getUserId(String jwt){
        if(jwt == null || !jwtService.validateJwt(jwt) ){
            throw new  UnauthorizedException("Invalid JWT");
        }
        User user =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }

    //Get all posts
    @GetMapping
    public ResponseEntity<?> getPosts(@CookieValue(name="fwt", required = false )String jwt) {
        String id= getUserId(jwt);
        List<PostResponse> posts = postService.getAllPosts();
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    //Create post
    @PostMapping
    public ResponseEntity<?> post(@CookieValue(name="jwt",required = false) String jwt ,@RequestBody PostRequest postRequest) {
        String userId = getUserId(jwt);
        PostResponse p = postService.createPost(postRequest);
        return ResponseEntity.status(HttpStatus.OK).body("created post successfully");
    }

    //Get following's posts
    @GetMapping("/following")
    public ResponseEntity<?> getPostFollowing(@CookieValue(name="fwt", required = false )String jwt) {
        String id= getUserId(jwt);
        List<PostResponse> posts = postService.getAllPostsFromFollowing(id);
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    //Get most liked posts
    @GetMapping("/Trending")
    public ResponseEntity<?> getTrendingPosts(@CookieValue(name="fwt", required = false )String jwt) {
        String id= getUserId(jwt);
        List<PostResponse> posts = postService.getTrendingPosts();
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    //Get newest posts
    @GetMapping("/newest")
    public ResponseEntity<?> getNewestPosts(@CookieValue(name="fwt", required = false ) String jwt) {
        String id= getUserId(jwt);
        List<PostResponse> posts = postService.getNewestPosts();
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

}
