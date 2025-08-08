package vn.com.chunean.chunean.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.com.chunean.chunean.dto.response.FollowingResponse;
import vn.com.chunean.chunean.dto.response.UserResponse;
import vn.com.chunean.chunean.entity.User;
import vn.com.chunean.chunean.exception.ResourceNotFoundException;
import vn.com.chunean.chunean.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final FollowingService followingService;

    public UserResponse userResponseMapping (User user, List<FollowingResponse> followingList, List<FollowingResponse> followerList) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setBio(user.getBio());
        userResponse.setAvatarUrl(user.getAvatarUrl());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setFollowingList(followingList);
        userResponse.setFollowerList(followerList);
        return userResponse;
    }

    public UserResponse getUserById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<FollowingResponse> followingList = followingService.getAllFollowing(user.getId());
        List<FollowingResponse> followerList = followingService.getAllFollower(user.getId());
        return userResponseMapping(user, followingList, followerList);
    }

    public List<UserResponse> findUserByUsername(String username) {
        List<User> userList = userRepository.seachUserByUsername(username);
        if (userList.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        return userList.stream().map(user ->{
            List<FollowingResponse> followingList = followingService.getAllFollowing(user.getId());
            List<FollowingResponse> followerList = followingService.getAllFollower(user.getId());
            return userResponseMapping(user, followingList, followerList);
        }).collect(Collectors.toList());
    }

}
