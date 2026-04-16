package com.isp.repository;

import com.isp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByMikrotikUsername(String mikrotikUsername);
    List<User> findByPhoneContainingOrMikrotikUsernameContaining(String phone, String username);
    User findByMikrotikUsername(String mikrotikUsername);
}