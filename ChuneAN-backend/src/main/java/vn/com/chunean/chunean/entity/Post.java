package vn.com.chunean.chunean.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "Posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @Column(name = "PostID", length = 30)
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
    private LocalDate createAt;
    @ManyToOne
    @JoinColumn(name = "UserID", referencedColumnName = "UserID")
    private User user;
}

