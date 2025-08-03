package vn.com.chunean.chunean.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.com.chunean.chunean.dto.response.UserResponse;
import vn.com.chunean.chunean.entity.Following;
import vn.com.chunean.chunean.entity.User;
import vn.com.chunean.chunean.exception.ResourceNotFoundException;
import vn.com.chunean.chunean.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final FollowingService followingService;

    public UserResponse userResponseMapping (User user, List<Following> followingList, List<Following> followerList) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setBio(user.getBio());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setFollowingList(followingList);
        return userResponse;
    }

    public UserResponse getUserById(String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        List<Following> followingList = followingService.getAllFollowing(user.get().getId());
        List<Following> followerList = followingService.getAllFollower(user.get().getId());
        return userResponseMapping(user.get(), followingList,followerList);
    }
}
