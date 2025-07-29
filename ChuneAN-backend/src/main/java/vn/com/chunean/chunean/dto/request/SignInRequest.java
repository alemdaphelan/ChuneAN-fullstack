package vn.com.chunean.chunean.dto.request;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class SignInRequest {
    String username;
    String password;
    String email;
    String birthday;
}
