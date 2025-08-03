package vn.com.chunean.chunean.dto.response;

import lombok.*;
import vn.com.chunean.chunean.entity.Following;

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
    private List<Following> followingList;
    private LocalDateTime createdAt;
}
