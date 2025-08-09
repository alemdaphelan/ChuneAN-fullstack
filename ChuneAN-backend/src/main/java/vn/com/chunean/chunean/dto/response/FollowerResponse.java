package vn.com.chunean.chunean.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowerResponse {
    String userId;
    String username;
    String avatarUrl;
}
