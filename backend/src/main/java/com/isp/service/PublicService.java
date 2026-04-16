package com.isp.service;
import com.isp.DTO.BillRequestDTO;
import com.isp.entity.Bill;
import org.springframework.stereotype.Service;
import com.isp.entity.User;
import com.isp.repository.UserRepository;
import java.util.List;
import com.isp.exception.ResourceNotFoundException;


@Service
public class PublicService {

        private final UserRepository userRepository;
        private final BillService billService;

    public PublicService(UserRepository userRepository, BillService billService) {
        this.userRepository = userRepository;
        this.billService = billService;
    }

    public List<Bill> getBills(BillRequestDTO dto) throws Exception {

        User user = userRepository.findByMikrotikUsername(dto.getUsername().trim());

        if(user == null) {
            throw new ResourceNotFoundException("User not found");
        }

        if (!user.getPhone().equals(dto.getPhone().trim())) {
            throw  new IllegalArgumentException("Invalid Credentials");
        }

        return billService.getBillsByUsername(user.getMikrotikUsername());
    }
}