package vn.com.chunean.chunean.request;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SignInRequest {
    String username;
    String password;
    String email;
    String birthday;
}
