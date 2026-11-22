package com.example.SpringSecurity.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Audited
@ToString(exclude = {
        "password",
        "aadhaarCard",
        "transactions"//because Lombok recursively prints both sides of the relationship. causing StackOverflowError
})
@EqualsAndHashCode(
        callSuper = true,
        exclude = {
                "aadhaarCard",
                "transactions"
        }//Collections and bidirectional relationships should not participate in equality checks.
)
public class CustomerEntity extends AuditableBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Column(nullable = false, unique = true)
    private String customerNumber;

    @Column(nullable = false, unique = true)
    private String username;

    /**
     * BCrypt Encoded Password
     */
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String mobileNumber;

    /**
     * CUSTOMER
     * ADMIN
     * MANAGER
     */
    @Column(nullable = false)
    private String role;

    @Builder.Default
    private boolean enabled = true;

    /**
     * =====================================================
     * ONE CUSTOMER -> ONE AADHAAR CARD
     *
     * Bidirectional Mapping
     *
     * CustomerEntity
     *      ↔
     * AadhaarCardEntity
     * =====================================================
     */
    @OneToOne(
            mappedBy = "customer",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private AadhaarCardEntity aadhaarCard;

    /**
     * =====================================================
     * ONE CUSTOMER -> MANY TRANSACTIONS
     *
     * Bidirectional Mapping
     *
     * CustomerEntity
     *      ↔
     * TransactionEntity
     * =====================================================
     */
    @OneToMany(
            mappedBy = "customer",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private List<TransactionEntity> transactions;
}