package com.example.SpringSecurity.repositories;

import com.example.SpringSecurity.entities.CustomerEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * ╔══════════════════════════════════════════════════════════════════════╗
 * ║                     CUSTOMER REPOSITORY                            ║
 * ╚══════════════════════════════════════════════════════════════════════╝
 *
 * ENTITY RELATIONSHIPS
 *
 * CustomerEntity
 *      │
 *      ├──────────► AadhaarCardEntity (One-To-One)
 *      │
 *      └──────────► TransactionEntity (One-To-Many)
 *
 *
 * IMPORTANT FETCH STRATEGY RULE
 *
 * We keep ALL relationships LAZY:
 *
 * @OneToOne(fetch = FetchType.LAZY)
 * @OneToMany(fetch = FetchType.LAZY)
 * @ManyToOne(fetch = FetchType.LAZY)
 *
 * WHY?
 *
 * Because EAGER loading can load huge object graphs
 * unnecessarily and significantly impact performance.
 *
 *
 * N+1 QUERY PROBLEM
 *
 * Example:
 *
 * List<CustomerEntity> customers =
 *      customerRepository.findAll();
 *
 * for(CustomerEntity customer : customers){
 *
 *      customer.getTransactions();
 * }
 *
 *
 * Hibernate Executes:
 *
 * Query 1:
 *
 * select * from customers;
 *
 *
 * Query 2:
 *
 * select *
 * from transactions
 * where customer_id = 1;
 *
 *
 * Query 3:
 *
 * select *
 * from transactions
 * where customer_id = 2;
 *
 *
 * Query 4:
 *
 * select *
 * from transactions
 * where customer_id = 3;
 *
 *
 * Total Queries:
 *
 * 1 + N
 *
 * This is called:
 *
 * N+1 Query Problem
 *
 *
 * SOLUTION
 *
 * NEVER switch everything to EAGER.
 *
 * Instead use:
 *
 * ✅ EntityGraph
 * ✅ Join Fetch
 * ✅ DTO Projections
 *
 *
 * Recommended Approach:
 *
 * Keep relationships LAZY.
 *
 * Fetch only what is needed through repository methods.
 *
 */

@Repository
public interface CustomerRepository
        extends JpaRepository<CustomerEntity, Long> {

    /**
     * ============================================================
     * SPRING SECURITY LOGIN
     * ============================================================
     *
     * Used By:
     *
     * UserDetailsService
     *
     * loadUserByUsername(...)
     */
    Optional<CustomerEntity> findByUsername(
            String username);



    /**
     * ============================================================
     * DUPLICATE CUSTOMER CHECKS
     * ============================================================
     */

    boolean existsByUsername(
            String username);

    boolean existsByEmail(
            String email);

    boolean existsByCustomerNumber(
            String customerNumber);



    /**
     * ============================================================
     * SEARCH CUSTOMER BY EMAIL
     * ============================================================
     *
     * Useful For:
     *
     * Forgot Password
     * Customer Search
     * Profile Lookup
     */
    Optional<CustomerEntity> findByEmail(
            String email);



    /**
     * ============================================================
     * FETCH CUSTOMER WITH AADHAAR
     * ============================================================
     *
     * LAZY relationship is overridden here.
     *
     * Hibernate generates a join query and fetches:
     *
     * Customer
     * Aadhaar
     *
     * in a single database call.
     *
     * Prevents:
     *
     * LazyInitializationException
     * N+1
     */
    @EntityGraph(
            attributePaths = {
                    "aadhaarCard"
            }
    )
    Optional<CustomerEntity>
    findCustomerWithAadhaarByCustomerId(
            Long customerId);



    /**
     * ============================================================
     * FETCH CUSTOMER WITH TRANSACTIONS
     * ============================================================
     *
     * Useful For:
     *
     * Transaction History
     * Mini Statement
     * Account Dashboard
     *
     * Loads:
     *
     * Customer
     * Transaction List
     *
     * in one optimized query.
     */
    @EntityGraph(
            attributePaths = {
                    "transactions"
            }
    )
    Optional<CustomerEntity>
    findCustomerWithTransactionsByCustomerId(
            Long customerId);



    /**
     * ============================================================
     * FETCH COMPLETE CUSTOMER DETAILS
     * ============================================================
     *
     * Loads:
     *
     * Customer
     * Aadhaar
     * Transactions
     *
     * Single Optimized Query
     *
     * Useful For:
     *
     * Customer Profile Screen
     * Admin Dashboard
     * Customer Details View
     */
    @EntityGraph(
            attributePaths = {
                    "aadhaarCard",
                    "transactions"
            }
    )
    Optional<CustomerEntity>
    findCustomerDetailsByCustomerId(
            Long customerId);



    /**
     * ============================================================
     * FETCH ALL CUSTOMERS + AADHAAR
     * ============================================================
     *
     * Without EntityGraph:
     *
     * Query 1:
     *
     * select * from customers
     *
     * Query 2..N:
     *
     * select * from aadhaar_cards
     * where customer_id = ?
     *
     *
     * N+1 Problem
     *
     *
     * With EntityGraph:
     *
     * Hibernate performs JOIN and retrieves
     * all required information in a single query.
     */
    @Override
    @EntityGraph(
            attributePaths = {
                    "aadhaarCard"
            }
    )
    List<CustomerEntity> findAll();



    /**
     * ============================================================
     * JOIN FETCH EXAMPLE
     * ============================================================
     *
     * Alternative To EntityGraph
     *
     * EntityGraph:
     *      Cleaner
     *      Preferred
     *
     * Join Fetch:
     *      More Control
     *      Useful For Complex Queries
     */
    @Query("""
            SELECT c
            FROM CustomerEntity c
            LEFT JOIN FETCH c.aadhaarCard
            WHERE c.customerId = :customerId
            """)
    Optional<CustomerEntity>
    findUsingJoinFetch(
            Long customerId);
}