package com.isp.service;

import com.isp.DTO.UserRequestDTO;
import com.isp.DTO.UserResponseDTO;
import com.isp.entity.Bill;
import com.isp.entity.Package;
import com.isp.entity.User;
import com.isp.exception.ResourceNotFoundException;
import com.isp.repository.UserRepository;
import com.isp.repository.PackageRepository;
import com.isp.util.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.isp.repository.BillRepository;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PackageRepository packageRepository;
    private final BillRepository billRepository;
    private final RouterService routerService;

    public UserService(UserRepository userRepository, PackageRepository packageRepository, BillRepository billRepository, RouterService routerService) {
        this.userRepository = userRepository;
        this.packageRepository = packageRepository;
        this.billRepository = billRepository;
        this.routerService = routerService;
    }


    public User createUser(UserRequestDTO dto) throws Exception {
        System.out.println("🔥 createUser called");
        System.out.println("Saving user: " + dto.mikrotikUsername);

        Package pkg = packageRepository.findById(dto.packageId)
                .orElseThrow(() -> new ResourceNotFoundException("Package not found"));

        if (userRepository.existsByMikrotikUsername(dto.mikrotikUsername)) {
            throw new ResourceNotFoundException("UserName already exists");
        }
        User user = new User();
        user.setName(dto.name);
        user.setPhone(dto.phone);
        user.setAddress(dto.address);
        user.setMikrotikUsername(dto.mikrotikUsername);
        user.setUserPackage(pkg);
        user.setStatus("ACTIVE");
        User savedUser = userRepository.save(user);
        createMikrotikUser(savedUser);
        return savedUser;
    }

    private void createMikrotikUser(User user) throws Exception {
        String username = user.getMikrotikUsername();
        String password = "";
        routerService.createUser(user);
    }

    @Scheduled(cron = "0 0 0 * * ?") // every day at midnight
    public void disableUnpaidUsers() throws Exception {
        LocalDate now = LocalDate.now();
        List<Bill> overdueBills = billRepository.findAll().stream()
                .filter(b -> b.getStatus().equals("UNPAID") && b.getBillingMonth().plusMonths(1).isBefore(now))
                .toList();

        for (Bill bill : overdueBills) {
            User user = bill.getUser();
            user.setStatus("INACTIVE");
            userRepository.save(user);


            routerService.disableUser(user);
        }
    }

    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
    List<User> users = userRepository.findAll();

    List<UserResponseDTO> response = users.stream()
            .map(UserResponseDTO::new)
            .toList();

    return response;
    }

    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null) {
            return null;
        }
        return new UserResponseDTO(user);
    }

    public UserResponseDTO updateUser(Long id,UserRequestDTO dto){
        User existingUser = userRepository.findById(id).orElse(null);
        if(existingUser == null) {
            return null;
        }

        existingUser.setName(dto.name);
        existingUser.setPhone(dto.phone);
        existingUser.setAddress(dto.address);
        existingUser.setMikrotikUsername(dto.mikrotikUsername);

        userRepository.save(existingUser);

        return new UserResponseDTO(existingUser);
    }

    public User deleteUser(Long id) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null) {
            return null;
        }
        userRepository.deleteById(id);
        return existingUser;
    }
}
