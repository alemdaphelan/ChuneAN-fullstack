package vn.com.chunean.chunean.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LikeRequest {
    private String userId;
    private String postId;
    private LocalDate createdAt;
}
