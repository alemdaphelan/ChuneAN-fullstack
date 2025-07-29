package vn.com.chunean.chunean.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;
import java.time.LocalDate;

@Entity
@Table(name = "Likes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Like {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Id
    @Column(name = "LikeID", length = 36)
    private String id = UUID.randomUUID().toString();
    @Column(name = "Created_at")
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "UserID", referencedColumnName = "UserID")
    private User user;
    @ManyToOne
    @JoinColumn(name = "PostID", referencedColumnName = "PostID", nullable = true)
    private Post post;
}
