package vn.com.chunean.chunean.services;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.chunean.chunean.dto.request.PostRequest;
import vn.com.chunean.chunean.dto.response.PostResponse;
import vn.com.chunean.chunean.entity.Post;
import vn.com.chunean.chunean.entity.User;
import vn.com.chunean.chunean.repositories.PostRepository;
import vn.com.chunean.chunean.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostResponse createPost(PostRequest request) {
        Optional<User> userOptional = userRepository.findById(request.getUserId());
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Not Found");
        }
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setCommentCount(request.getCommentCount());
        post.setLikeCount(request.getLikeCount());
        post.setCreatedAt(request.getCreatedAt());
        post.setContent(request.getContent());
        post.setTrackUrl(request.getTrackUrl());
        post.setUser(userOptional.get());
        Post saved = postRepository.save(post);

        PostResponse response = new PostResponse();
        response.setId(saved.getId());
        response.setContent(saved.getContent());
        response.setCommentCount(saved.getCommentCount());
        response.setLikeCount(saved.getLikeCount());
        response.setTitle(saved.getTitle());
        response.setTrackUrl(saved.getTrackUrl());
        response.setUsername(saved.getUser().getUsername());
        return response;
    }

    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream().map(post -> {
            PostResponse p = new PostResponse();
            p.setId(post.getId());
            p.setTitle(post.getTitle());
            p.setContent(post.getContent());
            p.setTrackUrl(post.getTrackUrl());
            p.setUsername(post.getUser().getUsername());
            p.setLikeCount(post.getLikeCount());
            p.setCommentCount(post.getCommentCount());
            return p;
        }).collect(Collectors.toList());
    }
}
