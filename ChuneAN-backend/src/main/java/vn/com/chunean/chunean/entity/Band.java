package vn.com.chunean.chunean.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Bands")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Band {
    @Id
    @Column(name = "BandID",length = 36)
    private String id = UUID.randomUUID().toString();
    @Column(name = "BandName",nullable = false, unique = true,length = 200)
    private  String bandName;
    @Column(name = "Create_at",nullable = false)
    private LocalDate CreateAt;
    @PrePersist
    protected void onCreate()
    {
        this.CreateAt = LocalDate.now();
    }

}
