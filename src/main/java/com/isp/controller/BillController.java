package com.isp.controller;

import com.isp.entity.Bill;
import com.isp.service.BillService;
import com.isp.service.UserService;
import com.isp.util.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    private final BillService billService;
    private final UserService userService;

    public BillController(BillService billService, UserService userService) {
        this.billService = billService;
        this.userService = userService;
    }

    // Generate bills
    @PostMapping("/generate")
    public String generateBills() {
        billService.generateMonthlyBills();
        return "Bills generated successfully";
    }

    // Pay bill
    @PostMapping("/pay/{id}")
    public ResponseEntity<ApiResponse<Bill>> payBill(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(ApiResponse.success("Bill paid successfully", billService.payBill(id)));
    }

    // Get all bills
    @GetMapping
    public ResponseEntity<ApiResponse<List<Bill>>> getAllBills() {

        return ResponseEntity.ok(ApiResponse.success("Bills retrieved successfully", billService.getAllBills()));
    }

    @PostMapping("/generatae/{userId}")
    public ResponseEntity<ApiResponse<Bill>> generateBillForUser(@PathVariable Long userId) throws Exception {
        return ResponseEntity.ok(ApiResponse.success("Bill generated successfully", billService.generateBillForUser(userId)));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Bill>>> getBillsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.success("Bills retrieved successfully", billService.getBillsByUserId(userId)));
    }

    @PostMapping("/disable-unpaid")
    public String disableUnpaidUsers() throws Exception {
        userService.disableUnpaidUsers();
        return "Unpaid users disabled successfully";
    }
}
