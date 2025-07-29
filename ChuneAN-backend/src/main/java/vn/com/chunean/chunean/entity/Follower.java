package vn.com.chunean.chunean.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "Followers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Follower {
    @Id
    @Column(name = "FollowerID",length = 36)
    private String id = UUID.randomUUID().toString();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID",referencedColumnName = "UserID",nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FollowerID",referencedColumnName = "FollowerID",nullable = false)
    private User follower;

}
