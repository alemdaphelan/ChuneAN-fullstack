package vn.com.chunean.chunean.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.com.chunean.chunean.dto.request.CommentRequest;
import vn.com.chunean.chunean.dto.response.CommentResponse;
import vn.com.chunean.chunean.entity.User;
import vn.com.chunean.chunean.exception.UnauthorizedException;
import vn.com.chunean.chunean.services.CommentService;
import vn.com.chunean.chunean.services.JwtService;

import java.util.List;

@RestController
@RequestMapping("/api/users/posts")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final JwtService jwtService;

    public User getUserByJwt(String jwt) {
        if(jwt == null || !jwtService.validateJwt(jwt)){
            throw new UnauthorizedException("Unauthorized");
        }
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @PostMapping("/comments/{id}")
    public ResponseEntity<CommentResponse> createComment(@CookieValue(name="jwt") String jwt, @PathVariable(name="id") String postId, @RequestBody CommentRequest request)
    {
        User user = getUserByJwt(jwt);
        request.setUserId(user.getId());
        request.setPostId(postId);
        CommentResponse response = commentService.createComment(request);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/comments/{id}")
    public ResponseEntity<List<CommentResponse>> getCommentsByPost (@PathVariable("id") String postId)
    {
        List<CommentResponse> responses =commentService.getCommentByPost(postId);
        return ResponseEntity.ok(responses);
    }
}

