package vn.com.chunean.chunean.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LikeRequest {
    private String userId;
    private String postId;
    private LocalDateTime createdAt;
}
