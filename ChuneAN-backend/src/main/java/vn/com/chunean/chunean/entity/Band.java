package vn.com.chunean.chunean.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Bands")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Band {
    @Id
    @Column(name = "id",length = 36)
    private String id;
    @Column(name = "BandName",nullable = false, unique = true,length = 200)
    private  String bandName;
    @Column(name = "Create_at",nullable = false)
    private LocalDateTime CreateAt;
    @PrePersist
    protected void onCreate()
    {
        if(this.id==null){
            this.id = UUID.randomUUID().toString();
        }
        this.CreateAt = LocalDateTime.now();
    }

}
