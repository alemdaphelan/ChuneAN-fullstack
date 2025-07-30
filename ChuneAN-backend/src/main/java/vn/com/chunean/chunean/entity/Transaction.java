package vn.com.chunean.chunean.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Transactions")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @Column(name = "id", length = 36)
    private String id = UUID.randomUUID().toString();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ToUserId", referencedColumnName = "id", nullable = false)
    private User toUser;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FromUserId", referencedColumnName = "id", nullable = false)
    private User fromUser;
    @Column(name = "AmountToken", nullable = false)
    private Double amountToken;
    @Column(name = "Created_at")
    private LocalDateTime createdAt;
    @PrePersist
    protected void onCreate() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
        this.createdAt = LocalDateTime.now();
    }

}
