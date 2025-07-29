package vn.com.chunean.chunean.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.chunean.chunean.dto.request.CommentRequest;
import vn.com.chunean.chunean.dto.response.CommentResponse;
import vn.com.chunean.chunean.entity.Comment;
import vn.com.chunean.chunean.entity.Post;
import vn.com.chunean.chunean.entity.User;
import vn.com.chunean.chunean.repositories.CommentRepository;
import vn.com.chunean.chunean.repositories.PostRepository;
import vn.com.chunean.chunean.repositories.UserRepository;
import java.util.Optional;

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
        comment.setCreateAt(request.getCreateAt());

        Comment saved = commentRepository.save(comment);

        CommentResponse response = new CommentResponse();
        response.setId(saved.getId());
        response.setContent(saved.getContent());
        response.setUsername(saved.getUser().getUsername());
        response.setPostId(saved.getPost().getId());

        return response;
    }
}
