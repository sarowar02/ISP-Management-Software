package com.isp.repository;

import com.isp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByMikrotikUsername(String mikrotikUsername);
}