package vn.com.chunean.chunean.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FollowingRequest {
    String userId;
    String followingId;
}
