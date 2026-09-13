package com.foxforge.api.service.rule;

import com.foxforge.api.domain.dto.PcBuildRequest;
import com.foxforge.api.domain.entity.CpuEntity;
import com.foxforge.api.domain.entity.MotherboardEntity;
import com.foxforge.api.domain.entity.RamEntity;
import com.foxforge.api.repository.CpuRepository;
import com.foxforge.api.repository.MotherboardRepository;
import com.foxforge.api.repository.RamRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

// Rule to validate memory generation compatibility among RAM, Motherboard, and CPU
@Component
public class MemoryMotherboardGenerationRule implements CompatibilityRule {

    private final RamRepository ramRepository;
    private final MotherboardRepository motherboardRepository;
    private final CpuRepository cpuRepository;

    // Injects repositories through constructor for Spring IoC
    public MemoryMotherboardGenerationRule(RamRepository ramRepository,
                                          MotherboardRepository motherboardRepository,
                                          CpuRepository cpuRepository) {
        this.ramRepository = ramRepository;
        this.motherboardRepository = motherboardRepository;
        this.cpuRepository = cpuRepository;
    }

    @Override
    public boolean isSatisfiedBy(PcBuildRequest request) {
        // Skip validation if any relevant component ID is missing
        if (request.ramId() == null || request.motherboardId() == null || request.cpuId() == null) {
            return true;
        }

        // Fetch entities from database
        Optional<RamEntity> ramOptional = ramRepository.findById(request.ramId());
        Optional<MotherboardEntity> motherboardOptional = motherboardRepository.findById(request.motherboardId());
        Optional<CpuEntity> cpuOptional = cpuRepository.findById(request.cpuId());

        // Incompatible if any required component is not found in database
        if (ramOptional.isEmpty() || motherboardOptional.isEmpty() || cpuOptional.isEmpty()) {
            return false;
        }

        RamEntity ram = ramOptional.get();
        MotherboardEntity motherboard = motherboardOptional.get();
        CpuEntity cpu = cpuOptional.get();

        String ramType = ram.getMemoryType();
        String motherboardMemoryType = motherboard.getMemoryType();
        String cpuSupportedMemory = cpu.getSupportedMemoryType();

        // 1. RAM memory type must match motherboard slot type (e.g., DDR4 vs DDR4)
        boolean matchesMotherboard = ramType.equalsIgnoreCase(motherboardMemoryType);

        // 2. CPU memory controller must support the RAM generation (e.g., "DDR4, DDR5" contains "DDR4")
        boolean matchesCpu = cpuSupportedMemory != null &&
                cpuSupportedMemory.toUpperCase().contains(ramType.toUpperCase());

        return matchesMotherboard && matchesCpu;
    }

    @Override
    public String getErrorMessage() {
        return "The chosen RAM generation (DDR) is not compatible with the selected Motherboard or CPU memory controller.";
    }
}
