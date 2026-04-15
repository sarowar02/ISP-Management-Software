package com.isp.service;

import com.isp.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary // 🔥 This makes Spring use this by default
public class MockRouterService implements RouterService {

    private static final Logger log = LoggerFactory.getLogger(MockRouterService.class);

    @Override
    public void createUser(User user) {
        log.info("🖥️ [MOCK ROUTER] Create user: {}", user.getMikrotikUsername());
    }

    @Override
    public void disableUser(User user) {
        log.warn("🛑 [MOCK ROUTER] Disable user: {}", user.getMikrotikUsername());
    }

    @Override
    public void enableUser(User user) {
        log.info("✅ [MOCK ROUTER] Enable user: {}", user.getMikrotikUsername());
    }
}