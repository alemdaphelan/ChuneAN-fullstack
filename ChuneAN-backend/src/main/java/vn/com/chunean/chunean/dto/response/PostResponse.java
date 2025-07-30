package vn.com.chunean.chunean.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private String id;
    private String title;
    private String content;
    private String trackUrl;
    private String username;
    private Integer likeCount;
    private Long commentCount;
    private LocalDateTime createdAt;
}
