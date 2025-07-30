package vn.com.chunean.chunean.dto.request;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentRequest {
    private String content;
    private LocalDateTime createdAt;
    private String userId;
    private String postId;
}
