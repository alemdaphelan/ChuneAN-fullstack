package vn.com.chunean.chunean.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LikeRequest {
    private String userId;
    private String postId;
}
