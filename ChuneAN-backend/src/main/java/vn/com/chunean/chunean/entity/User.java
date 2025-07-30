package vn.com.chunean.chunean.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
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
    @Column(name = "id", length = 36)
    private String id;
    @Column(name = "UserName", nullable = false, unique = true, length = 50)
    private String username;
    @Column(name = "Password")
    private String password;
    @Column(name = "Email", nullable = false, unique = true, length = 50)
    private String email;
    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;
    @Column(name = "Birthday")
    private String birth;
    @Column(name = "Created_at")
    private LocalDateTime createdAt;
    @Column(name = "Avatar_url", length = 255)
    private String avatarUrl;
    @PrePersist
    protected void onCreate() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
        this.createdAt = LocalDateTime.now();
    }
}
