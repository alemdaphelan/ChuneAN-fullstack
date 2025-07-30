package vn.com.chunean.chunean.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "Followings")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Following {
    @Id
    @Column(name = "id ",length = 36)
    private String id=UUID.randomUUID().toString();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId", referencedColumnName = "id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FollowingId", referencedColumnName = "id", nullable = false)
    private User following;
}
