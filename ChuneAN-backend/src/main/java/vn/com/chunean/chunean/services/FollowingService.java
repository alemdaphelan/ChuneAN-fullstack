package vn.com.chunean.chunean.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.com.chunean.chunean.dto.request.FollowingRequest;
import vn.com.chunean.chunean.dto.response.FollowingResponse;
import vn.com.chunean.chunean.entity.Following;
import vn.com.chunean.chunean.entity.User;
import vn.com.chunean.chunean.exception.ResourceNotFoundException;
import vn.com.chunean.chunean.repositories.FollowingRepository;
import vn.com.chunean.chunean.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FollowingService {
    private final FollowingRepository followingRepository;
    private final UserRepository userRepository;

    public FollowingResponse followingResponseMapping(Following following) {
        FollowingResponse followingResponse = new FollowingResponse();
        followingResponse.setUserId(following.getFollowing().getId());
        followingResponse.setUsername(following.getFollowing().getUsername());
        followingResponse.setAvatarUrl(following.getFollowing().getAvatarUrl());
        return followingResponse;
    }
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
        Optional<User> following = userRepository.findById(followingRequest.getFollowingId());
        Optional<User> user = userRepository.findById(followingRequest.getUserId());
        if(following.isEmpty()){
            throw new ResourceNotFoundException("Following not found");
        }
        if(user.isEmpty()){
            throw new ResourceNotFoundException("User not found");
        }
        followingRepository.deleteFollowById(followingRequest.getUserId(),followingRequest.getFollowingId());
    }

    public List<FollowingResponse> getAllFollowing(String userId){
        List<Following> followingList = followingRepository.findFollowingByUserId(userId);
        return  followingList.stream().map(this::followingResponseMapping).collect(Collectors.toList());
    }

    public List<FollowingResponse> getAllFollower(String userId){
        List<Following> followerList = followingRepository.findFollowerByUserId(userId);
        return  followerList.stream().map(this::followingResponseMapping).collect(Collectors.toList());
    }
}
