package com.isp.controller;

import com.isp.DTO.UserRequestDTO;
import com.isp.DTO.UserResponseDTO;
import com.isp.entity.User;
import com.isp.service.UserService;
import com.isp.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(@Valid @RequestBody UserRequestDTO dto) throws Exception {
        User user = userService.createUser(dto);
        return ResponseEntity.ok(ApiResponse.success("User created",user));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success("Users retrieved", users));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(@PathVariable Long id) {
        UserResponseDTO user = userService.getUserById(id);
        if(user == null) {
            return ResponseEntity.ok(ApiResponse.fail("User not found"));
        }
        return ResponseEntity.ok(ApiResponse.success("User retrieved",user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO dto) throws Exception {
        UserResponseDTO updated = userService.updateUser(id,dto);
        return ResponseEntity.ok(ApiResponse.success("User updated",updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Long id) {
        User user = userService.deleteUser(id);
        if (user == null) {
            return ResponseEntity.ok(ApiResponse.fail("User not found"));
        }
        return ResponseEntity.ok(ApiResponse.success("User deleted", null));
    }


}
