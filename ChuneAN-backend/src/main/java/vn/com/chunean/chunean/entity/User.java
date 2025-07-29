package vn.com.chunean.chunean.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
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
    @Column(name = "UserID", length = 36)
    private String id = UUID.randomUUID().toString();
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Column(name = "Created_at")
    private LocalDate createdAt;
    @Column(name = "Avatar_url", length = 255)
    private String avatarUrl;

    public User(String username, String password, String email, String birth) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.birth = birth;
        this.createdAt = LocalDate.now();
    }

    public Object getPassword() {return password;
    }
}
