package vn.com.chunean.chunean.dto.request;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class LoginRequest {
    String usernameOrEmail;
    String password;
}
