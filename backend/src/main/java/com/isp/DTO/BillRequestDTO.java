package com.isp.DTO;


import jakarta.validation.constraints.*;

public class BillRequestDTO {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Phone number is required")
    private String phone;

    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phone;
    }

}
