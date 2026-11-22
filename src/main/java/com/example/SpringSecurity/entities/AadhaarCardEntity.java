package com.example.SpringSecurity.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "aadhaar_cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Audited
public class AadhaarCardEntity extends AuditableBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aadhaarCardId;

    @Column(nullable = false, unique = true)
    private String aadhaarNumber;

    /**
     * ======================================================
     * OWNING SIDE OF RELATIONSHIP
     * ======================================================
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "customer_id",
            referencedColumnName = "customerId",
            unique = true)
    private CustomerEntity customer;
}
