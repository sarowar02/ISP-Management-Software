package com.isp.controller;
import org.springframework.web.bind.annotation.*;
import com.isp.entity.Bill;
import com.isp.entity.User;
import com.isp.repository.BillRepository;
import com.isp.repository.UserRepository;
import com.isp.util.ApiResponse;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class PaymentController {

    private final BillRepository billRepository;
    private final UserRepository userRepository;

    public PaymentController(BillRepository billRepository, UserRepository userRepository) {
        this.billRepository = billRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/payments/initiate/{billId}")
    public ApiResponse<?> initiatePayment(@PathVariable Long billId) {


        Bill bill = billRepository.findById(billId).orElseThrow();

        // simulate success
        bill.setStatus("PAID");
        bill.setPaymentStatus("SUCCESS");

        User user = bill.getUser();
        user.setStatus("ACTIVE");

        billRepository.save(bill);
        userRepository.save(user);

        return ApiResponse.success("Payment successful", null);
    }
}
