package vn.com.chunean.chunean.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeResponse {
    private String id;
    private String username;
    private String postId;
    private LocalDate createdAt;
}
