package vn.com.chunean.chunean.dto.response;

import lombok.*;

import java.time.LocalDateTime;

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
    private LocalDateTime createdAt;
}
