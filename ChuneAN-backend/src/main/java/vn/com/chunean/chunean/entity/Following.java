package vn.com.chunean.chunean.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "Followings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Following {
    @Id
    @Column(name = "FollowingRecordID ",length = 36)
    private String id=UUID.randomUUID().toString();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", referencedColumnName = "UserID", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FollowingID", referencedColumnName = "UserID", nullable = false)
    private User following;
}
