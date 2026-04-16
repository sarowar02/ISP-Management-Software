package com.isp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bills")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private double amount;

    private String status; // PAID / UNPAID
    private String paymentStatus; // SUCCESS / FAILED / PENDING

    private LocalDate billingMonth; // e.g. 2026-04-01

    private LocalDate paidDate;
}
