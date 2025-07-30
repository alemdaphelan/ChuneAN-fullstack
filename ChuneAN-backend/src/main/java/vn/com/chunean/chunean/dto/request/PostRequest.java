package vn.com.chunean.chunean.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class PostRequest {
    private String userId;
    private String title;
    private String content;
    private String trackUrl;
    private Integer likeCount;
    private Long commentCount;
    private LocalDateTime createdAt;
}
