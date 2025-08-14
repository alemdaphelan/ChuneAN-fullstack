package vn.com.chunean.chunean.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.com.chunean.chunean.dto.request.LikeRequest;
import vn.com.chunean.chunean.dto.response.LikeResponse;
import vn.com.chunean.chunean.entity.Like;
import vn.com.chunean.chunean.entity.Post;
import vn.com.chunean.chunean.entity.User;
import vn.com.chunean.chunean.repositories.LikeRepository;
import vn.com.chunean.chunean.repositories.PostRepository;
import vn.com.chunean.chunean.repositories.UserRepository;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public LikeResponse likePost(LikeRequest request) {
        Optional<User> userOpt = userRepository.findById(request.getUserId());
        Optional<Post> postOpt = postRepository.findById(request.getPostId());

        if (userOpt.isEmpty() || postOpt.isEmpty()) {
            throw new RuntimeException("User or Post not found");
        }

        Like like = new Like();
        like.setUser(userOpt.get());
        like.setPost(postOpt.get());
        Like saved = likeRepository.save(like);

        LikeResponse response = new LikeResponse();
        response.setId(saved.getId());
        response.setUsername(saved.getUser().getUsername());
        response.setPostId(saved.getPost().getId());

        return response;
    }
    public void unlikePost(LikeRequest request)
    {
        Optional<User> userOpt =userRepository.findById(request.getUserId());
        Optional<Post> postOpt =postRepository.findById(request.getPostId());
        if(userOpt.isEmpty()||postOpt.isEmpty())
        {
            throw new RuntimeException("User or Post not found !");
        }
        likeRepository.deleteByUserAndPost(userOpt.get(),postOpt.get());
    }
    public long countByPost (String postId)
    {
        Post post=postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found "));
        return likeRepository.countByPost(post);
    }
}
