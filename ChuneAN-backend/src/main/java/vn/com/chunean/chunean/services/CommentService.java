package vn.com.chunean.chunean.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.com.chunean.chunean.dto.request.CommentRequest;
import vn.com.chunean.chunean.dto.response.CommentResponse;
import vn.com.chunean.chunean.entity.Comment;
import vn.com.chunean.chunean.entity.Post;
import vn.com.chunean.chunean.entity.User;
import vn.com.chunean.chunean.repositories.CommentRepository;
import vn.com.chunean.chunean.repositories.PostRepository;
import vn.com.chunean.chunean.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentResponse createComment(CommentRequest request) {
        Optional<User> userOpt = userRepository.findById(request.getUserId());
        Optional<Post> postOpt = postRepository.findById(request.getPostId());

        if (userOpt.isEmpty() || postOpt.isEmpty()) {
            throw new RuntimeException("User or Post not found");
        }

        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setUser(userOpt.get());
        comment.setPost(postOpt.get());

        Comment saved = commentRepository.save(comment);

        CommentResponse response = new CommentResponse();
        response.setId(saved.getId());
        response.setContent(saved.getContent());
        response.setUsername(saved.getUser().getUsername());
        response.setPostId(saved.getPost().getId());

        return response;
    }
    public List<CommentResponse> getCommentByPost(String postId)
    {
        Post post = postRepository.findById(postId).orElseThrow(()-> new RuntimeException("Post not found"));
        List<Comment>comments = commentRepository.findByPost(post);
        return comments.stream().map(comment -> {
            CommentResponse response = new CommentResponse();
            response.setId(comment.getId());
            response.setContent(comment.getContent());
            response.setPostId(postId);
            response.setUsername(comment.getUser().getUsername());
            response.setCreatedAt(comment.getCreatedAt());
            return response;
        }).collect(Collectors.toList());
    }
    public long countByPost(String postId)
    {
        Post post = postRepository.findById(postId).orElseThrow(()-> new RuntimeException("Post not found"));
        return commentRepository.countByPost(post);
    }
}
