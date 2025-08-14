package vn.com.chunean.chunean.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentRequest {
    private String content;
    private String userId;
    private String postId;
}
