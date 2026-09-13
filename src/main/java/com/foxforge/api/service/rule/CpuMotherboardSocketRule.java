package com.foxforge.api.service.rule;

import com.foxforge.api.domain.dto.PcBuildRequest;
import com.foxforge.api.domain.entity.CpuEntity;
import com.foxforge.api.domain.entity.MotherboardEntity;
import com.foxforge.api.repository.CpuRepository;
import com.foxforge.api.repository.MotherboardRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

// Rule to validate if the CPU socket matches the Motherboard socket
@Component
public class CpuMotherboardSocketRule implements CompatibilityRule {

    private final CpuRepository cpuRepository;
    private final MotherboardRepository motherboardRepository;

    // Injects repositories through constructor for Spring IoC
    public CpuMotherboardSocketRule(CpuRepository cpuRepository, MotherboardRepository motherboardRepository) {
        this.cpuRepository = cpuRepository;
        this.motherboardRepository = motherboardRepository;
    }

    @Override
    public boolean isSatisfiedBy(PcBuildRequest request) {
        // Skip validation if any component ID is missing
        if (request.cpuId() == null || request.motherboardId() == null) {
            return true;
        }

        // Fetch CPU and Motherboard from database
        Optional<CpuEntity> cpuOptional = cpuRepository.findById(request.cpuId());
        Optional<MotherboardEntity> motherboardOptional = motherboardRepository.findById(request.motherboardId());

        // Incompatible if either component is not found in database
        if (cpuOptional.isEmpty() || motherboardOptional.isEmpty()) {
            return false;
        }

        CpuEntity cpu = cpuOptional.get();
        MotherboardEntity motherboard = motherboardOptional.get();

        // Check whether both sockets match, ignoring case sensitivity
        return cpu.getSocket().equalsIgnoreCase(motherboard.getSocket());
    }

    @Override
    public String getErrorMessage() {
        return "The chosen CPU is not compatible with the selected Motherboard's socket.";
    }
}
