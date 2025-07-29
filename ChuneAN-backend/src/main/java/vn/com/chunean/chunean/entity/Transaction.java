package vn.com.chunean.chunean.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @Column(name = "TransactionID", length = 36)
    private String id = UUID.randomUUID().toString();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ToUserId", referencedColumnName = "UserID", nullable = false)
    private User toUser;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FromUserId", referencedColumnName = "UserID", nullable = false)
    private User fromUser;
    @Column(name = "AmountToken", nullable = false)
    private Double amountToken;
    @Column(name = "Created_at")
    private LocalDateTime createdAt;
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}
