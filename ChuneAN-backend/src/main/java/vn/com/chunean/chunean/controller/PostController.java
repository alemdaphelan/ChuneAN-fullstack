package vn.com.chunean.chunean.controller;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.com.chunean.chunean.dto.request.PostRequest;
import vn.com.chunean.chunean.dto.response.PostResponse;
import vn.com.chunean.chunean.entity.User;
import vn.com.chunean.chunean.exception.UnauthorizedException;
import vn.com.chunean.chunean.services.FileService;
import vn.com.chunean.chunean.services.JwtService;
import vn.com.chunean.chunean.services.PostService;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/users/posts")
public class PostController {
    final private JwtService jwtService;
    final private PostService postService;
    final private FileService fileService;

    private String getUserId(String jwt){
        if(jwt == null || !jwtService.validateJwt(jwt) ){
            throw new  UnauthorizedException("Invalid JWT");
        }
        User user =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }

    //Get all posts
    @GetMapping
    public ResponseEntity<?> getPosts(@CookieValue(name="jwt", required = false )String jwt) {
        String id= getUserId(jwt);
        List<PostResponse> posts = postService.getAllPosts();
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    //Create post and store file
    @PostMapping
    public ResponseEntity<?> post(@RequestParam(name="file", required = false)MultipartFile file, @CookieValue(name="jwt",required = false) String jwt , @ModelAttribute PostRequest postRequest) throws IOException {
        String userId = getUserId(jwt);
        String fileUrl = "";
        if(file != null) {
            fileUrl = fileService.storeFile(file);
        }
        postRequest.setTrackUrl(fileUrl);
        postRequest.setUserId(userId);
        PostResponse p = postService.createPost(postRequest);
        return ResponseEntity.status(HttpStatus.OK).body("post was created successfully");
    }

    //Get following's posts
    @GetMapping("/following")
    public ResponseEntity<?> getPostFollowing(@CookieValue(name="jwt", required = false )String jwt) {
        String id= getUserId(jwt);
        List<PostResponse> posts = postService.getAllPostsFromFollowing(id);
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    //Get most liked posts
    @GetMapping("/trending")
    public ResponseEntity<?> getTrendingPosts(@CookieValue(name="jwt", required = false )String jwt) {
        String id= getUserId(jwt);
        List<PostResponse> posts = postService.getTrendingPosts();
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    //Get newest posts
    @GetMapping("/newest")
    public ResponseEntity<?> getNewestPosts(@CookieValue(name="jwt", required = false ) String jwt) {
        String id= getUserId(jwt);
        List<PostResponse> posts = postService.getNewestPosts();
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

}
