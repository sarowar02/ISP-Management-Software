package com.isp.controller;
import com.isp.entity.Admin;
import com.isp.repository.AdminRepository;
import com.isp.util.ApiResponse;
import org.springframework.web.bind.annotation.*;
import com.isp.util.JWTUtil;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class AuthController {

    private final AdminRepository adminRepository;
    private final JWTUtil jWTUtil;

    public AuthController(AdminRepository adminRepository, JWTUtil jWTUtil) {
        this.adminRepository = adminRepository;
        this.jWTUtil = jWTUtil;
    }

    @PostMapping("/login")
    public ApiResponse<?> login(@RequestBody Admin admin) {

        Admin dbAdmin = adminRepository.findByUsername(admin.getUsername()) ;

        if(dbAdmin == null) {
            throw new RuntimeException("Admin not found");
        }

        if (!dbAdmin.getPassword().equals(admin.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jWTUtil.generateToken(dbAdmin.getUsername());

        return ApiResponse.success("Login successful", token);
    }

}
