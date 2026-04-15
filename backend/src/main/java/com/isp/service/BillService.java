package com.isp.service;

import com.isp.entity.Bill;
import com.isp.entity.User;
import com.isp.exception.ResourceNotFoundException;
import com.isp.repository.BillRepository;
import com.isp.repository.UserRepository;
import com.isp.util.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BillService {

    private final BillRepository billRepository;
    private final UserRepository userRepository;
    private final RouterService routerService;

    public BillService(BillRepository billRepository, UserRepository userRepository, RouterService routerService) {
        this.billRepository = billRepository;
        this.userRepository = userRepository;
        this.routerService = routerService ;
    }

    // 🔥 Generate monthly bills for ALL users
    public void generateMonthlyBills() {
        List<User> users = userRepository.findAll();

        LocalDate currentMonth = LocalDate.now().withDayOfMonth(1);

        for (User user : users) {

            if (!"ACTIVE".equalsIgnoreCase(user.getStatus())) {
                continue;
            }

            boolean exists = billRepository
                    .findByUserAndBillingMonth(user, currentMonth)
                    .isPresent();

            if (!exists) {
                Bill bill = new Bill();
                bill.setUser(user);
                bill.setAmount(user.getUserPackage().getPrice());
                bill.setStatus("UNPAID");
                bill.setBillingMonth(currentMonth);

                billRepository.save(bill);
            }
        }
    }

    // 💰 Pay bill
    public Bill payBill(Long billId) throws Exception {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new ResourceNotFoundException("bill not found"));

        bill.setStatus("PAID");
        bill.setPaidDate(LocalDate.now());

        User user = bill.getUser();
        user.setStatus("ACTIVE");
        userRepository.save(user);

        routerService.enableUser(user);

        return bill ;
    }

    // 📄 Get all bills
    public List<Bill> getAllBills() {

        List<Bill> bills =  billRepository.findAll();

        return bills;
    }

    public Bill generateBillForUser(Long userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        LocalDate currentMonth = LocalDate.now().withDayOfMonth(1);

        boolean exists = billRepository
                .findByUserAndBillingMonth(user, currentMonth)
                .isPresent();

        if (exists) {
            throw new Exception("Bill already exists for this user for the current month");
        }

        Bill bill = new Bill();
        bill.setUser(user);
        bill.setAmount(user.getUserPackage().getPrice());
        bill.setStatus("UNPAID");
        bill.setBillingMonth(currentMonth);

        return billRepository.save(bill);
    }

    public List<Bill> getBillsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return List.of();
        }
        return billRepository.findByUser(user);
    }

    public List<Bill> searchBills(String keyword) {
        List<Bill> result;

        if (keyword.equalsIgnoreCase("PAID") ||
                keyword.equalsIgnoreCase("UNPAID")) {

            result = billRepository.findByStatus(keyword.toUpperCase());

        } else {
            result = billRepository.findByUser_MikrotikUsernameContaining(keyword);
        }

        return result;
    }
}
