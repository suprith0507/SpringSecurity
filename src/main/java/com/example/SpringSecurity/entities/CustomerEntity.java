package com.example.SpringSecurity.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
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
public class CustomerEntity extends AuditableBaseEntity implements UserDetails {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of( new SimpleGrantedAuthority(
                "ROLE_" + role));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}