package com.isp.controller;

import com.isp.entity.Package;
import com.isp.service.PackageService;
import com.isp.util.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/packages")
public class PackageController {

    private final PackageService packageService;

    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Package>> createPackage(@RequestBody Package pkg) {
        return ResponseEntity.ok(ApiResponse.success("package created",packageService.createPackage(pkg)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Package>>> getAllPackages() {

        return ResponseEntity.ok(ApiResponse.success("Packages retrieved", packageService.getAllPackages()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Package>> getPackageById(@PathVariable Long id) {
        Package pkg = packageService.getPackageById(id);
        if(pkg == null) {
            return ResponseEntity.ok(ApiResponse.fail("Package not found"));
        }
        return ResponseEntity.ok(ApiResponse.success("Package retrieved",pkg));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Package>> updatePackage(@PathVariable Long id, @RequestBody Package pkg) {
        Package updated = packageService.updatePackage(id,pkg);
        return ResponseEntity.ok(ApiResponse.success("Package updated",updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deletePackage(@PathVariable Long id) {

        Package pkg = packageService.deletePackage(id);
        if (pkg == null) {
            return ResponseEntity.ok(ApiResponse.fail("Package not found"));
        }
        return ResponseEntity.ok(ApiResponse.success("Package deleted", null));
    }
}
