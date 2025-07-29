package vn.com.chunean.chunean.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
}
