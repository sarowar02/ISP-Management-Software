package com.isp.controller;
import com.isp.entity.Bill;
import com.isp.repository.BillRepository;
import com.isp.repository.PackageRepository;
import com.isp.repository.UserRepository;
import com.isp.util.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final UserRepository userRepository;
    private final PackageRepository packageRepository;
    private final BillRepository billRepository;

    public DashboardController(UserRepository userRepository,
                               PackageRepository packageRepository,
                               BillRepository billRepository) {
        this.userRepository = userRepository;
        this.packageRepository = packageRepository;
        this.billRepository = billRepository;

        System.out.println("DashboardController initialized");
    }

    @GetMapping("/stats")
    public ApiResponse<Map<String, Long>> getStats() {
        System.out.println("API HIT /dashboard/stats");

        long totalUsers = userRepository.count();
        long totalPackages = packageRepository.count();
        long totalBills = billRepository.count();
        long unpaidBills = billRepository.findAll().stream()
                .filter(b -> "UNPAID".equals(b.getStatus()))
                .count();

        double totalRevenue = billRepository.findAll().stream()
                .filter(bill -> "PAID".equals(bill.getStatus()))
                .mapToDouble(Bill::getAmount)
                .sum();
        double totalDue = billRepository.findAll().stream()
                .filter(bill -> "UNPAID".equals(bill.getStatus()))
                .mapToDouble(Bill::getAmount)
                .sum();
        System.out.println("Users count: " + userRepository.count());
        System.out.println("Packages count: " + packageRepository.count());
        System.out.println("Bills count: " + billRepository.count());
        System.out.println("Unpaid count: " + billRepository.countByStatus("UNPAID"));

        Map<String, Long> stats = new HashMap<>();
        stats.put("users", totalUsers);
        stats.put("packages", totalPackages);
        stats.put("bills", totalBills);
        stats.put("unpaid", unpaidBills);
        stats.put("revenue", (long) totalRevenue);
        stats.put("due",  (long) totalDue);

        return ApiResponse.success("Stats fetched", stats);
    }
}