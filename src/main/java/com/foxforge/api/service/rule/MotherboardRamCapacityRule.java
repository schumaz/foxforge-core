package com.foxforge.api.service.rule;

import com.foxforge.api.domain.dto.PcBuildRequest;
import com.foxforge.api.domain.entity.MotherboardEntity;
import com.foxforge.api.domain.entity.RamEntity;
import com.foxforge.api.repository.MotherboardRepository;
import com.foxforge.api.repository.RamRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

// Rule to validate if the physical RAM slots and maximum storage capacity (GB) are respected by the motherboard
@Component
public class MotherboardRamCapacityRule implements CompatibilityRule {

    private final MotherboardRepository motherboardRepository;
    private final RamRepository ramRepository;

    // Injects repositories through constructor for Spring IoC
    public MotherboardRamCapacityRule(MotherboardRepository motherboardRepository, RamRepository ramRepository) {
        this.motherboardRepository = motherboardRepository;
        this.ramRepository = ramRepository;
    }

    @Override
    public boolean isSatisfiedBy(PcBuildRequest request) {
        // Skip validation if Motherboard or RAM is not present in the request
        if (request.motherboardId() == null || request.ramId() == null) {
            return true;
        }

        // Fetch entities from database
        Optional<MotherboardEntity> motherboardOptional = motherboardRepository.findById(request.motherboardId());
        Optional<RamEntity> ramOptional = ramRepository.findById(request.ramId());

        // Incompatible if either component is not found in database
        if (motherboardOptional.isEmpty() || ramOptional.isEmpty()) {
            return false;
        }

        MotherboardEntity motherboard = motherboardOptional.get();
        RamEntity ram = ramOptional.get();

        Integer maxCapacityGb = motherboard.getMaxMemoryCapacityGb();
        Integer memorySlots = motherboard.getMemorySlots();
        
        Integer ramCapacityGb = ram.getCapacityGb();
        Integer ramModules = ram.getModulesCount();

        // Safe null-checking; if hardware limit constraints are unlisted, allow it to pass temporarily
        if (maxCapacityGb == null || memorySlots == null || ramCapacityGb == null || ramModules == null) {
            return true;
        }

        // 1. Physical slots validation
        boolean fitsInSlots = ramModules <= memorySlots;
        
        // 2. Maximum supported theoretical capacity validation
        boolean respectsCapacity = ramCapacityGb <= maxCapacityGb;

        return fitsInSlots && respectsCapacity;
    }

    @Override
    public String getErrorMessage() {
        return "The selected RAM memory kit exceeds the Motherboard's maximum supported capacity in GB or the physical number of memory slots available.";
    }
}
