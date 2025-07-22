package vn.com.chunean.chunean.request;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginRequest {
    String usernameOrEmail;

    public String getPassword() {
        return password;
    }

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    String password;
}
