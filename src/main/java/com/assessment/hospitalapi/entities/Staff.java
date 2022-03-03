package com.assessment.hospitalapi.entities;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

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

    @Column(unique=true)
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

    @PrePersist
    public void preSave() {
        if(StringUtils.isEmpty(uuid))
            uuid = UUID.randomUUID().toString();
    }
}
