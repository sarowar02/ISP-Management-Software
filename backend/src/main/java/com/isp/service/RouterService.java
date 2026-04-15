package com.isp.service;

import com.isp.entity.User;

public interface RouterService {

    void createUser(User user);

    void disableUser(User user);

    void enableUser(User user);
}
