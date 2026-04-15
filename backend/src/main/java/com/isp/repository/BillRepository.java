package com.isp.repository;

import com.isp.entity.Bill;
import com.isp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Long> {

    Optional<Bill> findByUserAndBillingMonth(User user, LocalDate billingMonth);
    List<Bill> findByUser(User user);
    long countByStatus(String status);

    List<Bill> findByStatus(String status);
    List<Bill> findByUser_MikrotikUsernameContaining(String username);
}
