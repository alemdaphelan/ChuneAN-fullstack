package vn.com.chunean.chunean.dto.response;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private String birth;
    private String avatarUrl;
    private String bio;
    private List<FollowingResponse> followingList;
    private List<FollowerResponse> followerList;
    private LocalDateTime createdAt;
}
