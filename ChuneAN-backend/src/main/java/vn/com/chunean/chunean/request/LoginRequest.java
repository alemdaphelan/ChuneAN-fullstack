package vn.com.chunean.chunean.request;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginRequest {
    String usernameOrEmail;
    String password;
}
