package vn.com.chunean.chunean.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CommentRequest {
    private String content;
    private LocalDate createAt;
    private String userId;
    private String postId;
}
