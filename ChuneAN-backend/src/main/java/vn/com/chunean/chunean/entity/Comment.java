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
    @Column(name = "CommentID", length = 30)
    private String id = UUID.randomUUID().toString();
    @Column(name = "Content", columnDefinition = "TEXT", nullable = false)
    private String content;
    @Column(name = "Created_at")
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "UserID", referencedColumnName = "UserID")
    private User user;
    @ManyToOne
    @JoinColumn(name = "PostID", referencedColumnName = "PostID")
    private Post post;
}
