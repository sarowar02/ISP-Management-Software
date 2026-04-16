package com.isp.controller;

import com.isp.DTO.BillRequestDTO;
import com.isp.service.PublicService;
import com.isp.entity.Bill;
import com.isp.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/public")
@CrossOrigin(origins = "http://localhost:3000")
public class PublicController {

    private final PublicService publicService;

    public PublicController(PublicService publicService) {
        this.publicService = publicService;
    }

    @PostMapping("/bills")
    public ApiResponse<List<Bill>> getBills(@Valid  @RequestBody BillRequestDTO dto) throws Exception {

        return ApiResponse.success("Bills retrieved",publicService.getBills(dto));
    }
}