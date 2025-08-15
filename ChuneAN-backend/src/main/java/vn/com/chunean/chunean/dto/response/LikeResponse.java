package vn.com.chunean.chunean.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikeResponse {
    private String username;
    private String postId;
    private LocalDateTime createdAt;
}
