package com.isp.DTO;

import jakarta.validation.constraints.*;

public class UserRequestDTO {

    @NotBlank(message = "Name is required")
    public String name;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "01[3-9]\\d{8}", message = "Invalid Bangladeshi phone number")
    public String phone;

    @NotBlank(message = "Address is required")
    public String address;

    @NotBlank(message = "Mikrotik username is required")
    @Size(min = 4, max = 20, message = "Username must be 4-20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username must be alphanumeric")
    public String mikrotikUsername;

    @NotNull(message = "Package ID is required")
    public Long packageId;

    // getters & setters
}