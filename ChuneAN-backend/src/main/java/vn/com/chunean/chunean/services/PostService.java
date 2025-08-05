package vn.com.chunean.chunean.services;

import lombok.*;
import org.springframework.stereotype.Service;
import vn.com.chunean.chunean.dto.request.PostRequest;
import vn.com.chunean.chunean.dto.response.PostResponse;
import vn.com.chunean.chunean.entity.Post;
import vn.com.chunean.chunean.entity.User;
import vn.com.chunean.chunean.exception.ResourceNotFoundException;
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

    private PostResponse mappingPostResponse (Post post) {
        PostResponse p = new PostResponse();
        p.setId(post.getId());
        p.setUserId(post.getUser().getId());
        p.setAvatarUrl(post.getUser().getAvatarUrl());
        p.setTitle(post.getTitle());
        p.setContent(post.getContent());
        p.setTrackUrl(post.getTrackUrl());
        p.setUsername(post.getUser().getUsername());
        p.setLikeCount(post.getLikeCount());
        p.setCommentCount(post.getCommentCount());
        p.setCreatedAt(post.getCreatedAt());
        return p;
    }

    public void createPost(PostRequest request) {
        Optional<User> userOptional = userRepository.findById(request.getUserId());
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setCommentCount(request.getCommentCount());
        post.setLikeCount(request.getLikeCount());
        post.setContent(request.getContent());
        post.setTrackUrl(request.getTrackUrl());
        post.setUser(userOptional.get());
        postRepository.save(post);
    }

    public List<PostResponse> getAllPosts() {
        return postRepository.findAllWithUser().stream().map(this::mappingPostResponse
        ).collect(Collectors.toList());
    }

    public List<PostResponse> getAllPostsFromFollowing(String userId) {
        List<Post> posts = postRepository.getAllPostsFromFollowing(userId);
        return posts.stream().map(this::mappingPostResponse).toList();
    }

    public List<PostResponse> getTrendingPosts() {
        List<Post> posts = postRepository.getTrendingPosts();
        return posts.stream().map(this::mappingPostResponse).toList();
    }

    public List<PostResponse> getNewestPosts(){
        List<Post> posts = postRepository.getNewestPost();
        return posts.stream().map(this::mappingPostResponse).toList();
    }
    public List<PostResponse> getPostsFromUserId(String userId){
        List<Post> posts = postRepository.getPostsByUserId(userId);
        return posts.stream().map(this::mappingPostResponse).toList();
    }
}
