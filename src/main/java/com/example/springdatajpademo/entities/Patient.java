package com.example.springdatajpademo.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(
        //name = "patient_table",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_patient_email", columnNames = {"email"}),
                @UniqueConstraint(name = "unique_patient_id",columnNames = {"id"})
        },
        indexes = {
                @Index(name = "idx_patient_birth_date" ,columnList = "birthDate")
        }
)
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private LocalDate birthDate;
    private String email;
    @Column(nullable = false)
    private String gender;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createAt;
    private String bloodGrp;
}
