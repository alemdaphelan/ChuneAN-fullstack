package vn.com.chunean.chunean.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Comment {
    @Id
    @Column(name = "id", length = 36)
    private String id;
    @Column(name = "Content", columnDefinition = "TEXT", nullable = false)
    private String content;
    @Column(name = "Created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if(this.id == null){
            this.id = UUID.randomUUID().toString();
        }
        this.createdAt = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "UserId", referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "PostId", referencedColumnName = "id")
    private Post post;
}
