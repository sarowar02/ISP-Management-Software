package com.isp.DTO;

import com.isp.entity.User;

public class UserResponseDTO {
    private Long id;
    private String name;
    private String phone;
    private String mikrotikUsername;
    private String packageName;
    private String status;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.phone = user.getPhone();
        this.mikrotikUsername = user.getMikrotikUsername();
        this.packageName = user.getUserPackage().getName();
        this.status = user.getStatus();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getMikrotikUsername() { return mikrotikUsername; }
    public String getPackageName() { return packageName; }
    public String getStatus() { return status; }
}