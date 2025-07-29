package vn.com.chunean.chunean.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "Comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @Column(name = "CommentID", length = 36)
    private String id = UUID.randomUUID().toString();
    @Column(name = "Content", columnDefinition = "TEXT", nullable = false)
    private String content;
    @Column(name = "Created_at")
    private LocalDate createdAt;
    private LocalDate createAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    @ManyToOne
    @JoinColumn(name = "UserID", referencedColumnName = "UserID")
    private User user;
    @ManyToOne
    @JoinColumn(name = "PostID", referencedColumnName = "PostID")
    private Post post;

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }
}
