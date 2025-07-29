package vn.com.chunean.chunean.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "Projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @Column(name = "ProjectID", length = 36)
    private String id = UUID.randomUUID().toString();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OwnerID", referencedColumnName = "UserID", nullable = false)
    private User owner;
    @Column(name = "Title", nullable = false, length = 100)
    private String title;
    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "Created_at", nullable = false)
    private LocalDate createdAt;
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now();
    }
}
