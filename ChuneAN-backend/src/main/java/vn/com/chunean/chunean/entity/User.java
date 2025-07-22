package vn.com.chunean.chunean.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Users")
public class User {
    @Id
    @Column(name = "UserID", length = 30)
    private String id = UUID.randomUUID().toString();
    @Column(name = "UserName", nullable = false, unique = true, length = 50)
    private String username;
    @Column(name = "Password", nullable = false)
    private String password;
    @Column(name = "Email", nullable = false, unique = true, length = 50)
    private String email;
    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;
    @Column(name = "Birthday")
    private String birth;
    @Column(name = "Created_at")
    private LocalDate createdAt;
    @Column(name = "Avatar_url", length = 255)
    private String avatarUrl;

    public User(String username, String password, String email, String birth) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.birth = birth;
    }

    public Object getPassword() {return password;
    }
}
