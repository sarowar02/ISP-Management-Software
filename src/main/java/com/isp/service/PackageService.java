package com.isp.service;

import com.isp.entity.Package;
import com.isp.repository.PackageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageService {

    private final PackageRepository packageRepository;

    public PackageService(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    public Package createPackage(Package pkg) {
        return packageRepository.save(pkg);
    }

    public List<Package> getAllPackages() {
        return packageRepository.findAll();
    }
    public Package getPackageById(Long id) {
        return packageRepository.findById(id).orElse(null);
    }
    public Package deletePackage(Long id) {
            Package existingPackage = packageRepository.findById(id).orElse(null);
            packageRepository.deleteById(id);
            return existingPackage;
    }
    public Package updatePackage(Long id , Package pkg){
        Package existingPackage = packageRepository.findById(id).orElse(null);
        if(existingPackage == null) {
            return null;
        }
        existingPackage.setName(pkg.getName());
        existingPackage.setPrice(pkg.getPrice());
        existingPackage.setSpeed(pkg.getSpeed());
        return packageRepository.save(existingPackage);
    }


}
