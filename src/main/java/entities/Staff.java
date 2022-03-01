package entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "staffs")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "staffs_pk_sequence")
    @SequenceGenerator(name = "staffs_pk_sequence", sequenceName = "staffs_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    public Staff() {
    }

    public Long getId() {
        return id;
    }

    private String name;
    private String uuid;

    @CreationTimestamp
    private LocalDateTime registrationDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }
}
