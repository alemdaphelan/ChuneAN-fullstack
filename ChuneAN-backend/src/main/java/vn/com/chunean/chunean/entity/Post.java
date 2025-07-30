package vn.com.chunean.chunean.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Posts")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @Column(name = "id", length = 36)
    private String id = UUID.randomUUID().toString();
    @Column(name = "Title", nullable = false)
    private String title;
    @Column(name = "Content", columnDefinition = "TEXT")
    private String content;
    @Column(name = "Track_url")
    private String trackUrl;
    @Column(name = "Like_count")
    private Integer likeCount;
    @Column(name = "Comment_count")
    private Long commentCount;
    @Column(name = "Create_at")
    private LocalDateTime createdAt;
    @PrePersist
    protected void onCreate() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
        this.createdAt = LocalDateTime.now();
    }
    @ManyToOne
    @JoinColumn(name = "UserId", referencedColumnName = "id")
    private User user;
}

