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
    @Id
    @Column(name = "LikeID", length = 30)
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
