package vn.com.chunean.chunean.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private String id;
    private String content;
    private String postId;
    private String username;
    private LocalDate createAt;
}
