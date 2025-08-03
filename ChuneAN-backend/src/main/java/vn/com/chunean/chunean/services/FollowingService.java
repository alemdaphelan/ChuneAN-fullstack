package vn.com.chunean.chunean.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.com.chunean.chunean.dto.request.FollowingRequest;
import vn.com.chunean.chunean.entity.Following;
import vn.com.chunean.chunean.entity.User;
import vn.com.chunean.chunean.exception.ResourceNotFoundException;
import vn.com.chunean.chunean.repositories.FollowingRepository;
import vn.com.chunean.chunean.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FollowingService {
    private final FollowingRepository followingRepository;
    private final UserRepository userRepository;

    public void createFollowing(FollowingRequest followingRequest) {

        Optional<User> user = userRepository.findById(followingRequest.getUserId());
        Optional<User> userFollow = userRepository.findById(followingRequest.getFollowingId());

        if(userFollow.isEmpty()){
            throw new ResourceNotFoundException("Following not found");
        }
        if(user.isEmpty()){
            throw new ResourceNotFoundException("User not found");
        }

        Following following = new Following();
        following.setUser(user.get());
        following.setFollowing(userFollow.get());
        followingRepository.save(following);
    }

    public void deleteFollowing(FollowingRequest followingRequest) {
        Optional<Following> following = followingRepository.findById(followingRequest.getFollowingId());
        Optional<Following> user = followingRepository.findById(followingRequest.getUserId());
        if(following.isEmpty()){
            throw new ResourceNotFoundException("Following not found");
        }
        if(user.isEmpty()){
            throw new ResourceNotFoundException("User not found");
        }
        followingRepository.deleteFollowById(followingRequest.getUserId(),followingRequest.getFollowingId());
    }

    public List<Following> getAllFollowing(String userId){
        return  followingRepository.findFollowingByUserId(userId);
    }

    public List<Following> getAllFollower(String userId){
        return  followingRepository.findFollowerByUserId(userId);
    }
}
