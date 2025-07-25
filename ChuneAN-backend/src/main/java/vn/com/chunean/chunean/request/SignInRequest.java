package vn.com.chunean.chunean.request;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SignInRequest {

    public String getUsername() {
        return username;
    }

    String username;

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthday() {
        return birthday;
    }

    String password;
    String email;
    String birthday;
}
