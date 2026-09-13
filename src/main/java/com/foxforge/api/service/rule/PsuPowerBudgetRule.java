package com.foxforge.api.service.rule;

import com.foxforge.api.domain.dto.PcBuildRequest;
import com.foxforge.api.domain.entity.PsuEntity;
import com.foxforge.api.repository.CpuRepository;
import com.foxforge.api.repository.GpuRepository;
import com.foxforge.api.repository.MotherboardRepository;
import com.foxforge.api.repository.PsuRepository;
import com.foxforge.api.repository.RamRepository;
import com.foxforge.api.repository.StorageRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

// Rule to validate if the PSU wattage safely supports the entire PC component power consumption with a margin
@Component
public class PsuPowerBudgetRule implements CompatibilityRule {

    private static final double SAFETY_MARGIN_MULTIPLIER = 1.20;

    private final PsuRepository psuRepository;
    private final CpuRepository cpuRepository;
    private final GpuRepository gpuRepository;
    private final MotherboardRepository motherboardRepository;
    private final RamRepository ramRepository;
    private final StorageRepository storageRepository;

    // Injects repositories through constructor for Spring IoC
    public PsuPowerBudgetRule(PsuRepository psuRepository,
                              CpuRepository cpuRepository,
                              GpuRepository gpuRepository,
                              MotherboardRepository motherboardRepository,
                              RamRepository ramRepository,
                              StorageRepository storageRepository) {
        this.psuRepository = psuRepository;
        this.cpuRepository = cpuRepository;
        this.gpuRepository = gpuRepository;
        this.motherboardRepository = motherboardRepository;
        this.ramRepository = ramRepository;
        this.storageRepository = storageRepository;
    }

    @Override
    public boolean isSatisfiedBy(PcBuildRequest request) {
        // Skip validation if PSU is not selected yet
        if (request.psuId() == null) {
            return true;
        }

        // Fetch PSU from database
        Optional<PsuEntity> psuOptional = psuRepository.findById(request.psuId());
        if (psuOptional.isEmpty()) {
            return false;
        }

        PsuEntity psu = psuOptional.get();
        if (psu.getWattage() == null) {
            return true;
        }

        int totalSystemTdp = 0;

        // Sum CPU TDP
        if (request.cpuId() != null) {
            var cpuOptional = cpuRepository.findById(request.cpuId());
            if (cpuOptional.isPresent() && cpuOptional.get().getTdpWatts() != null) {
                totalSystemTdp += cpuOptional.get().getTdpWatts();
            }
        }

        // Sum GPU TDP
        if (request.gpuId() != null) {
            var gpuOptional = gpuRepository.findById(request.gpuId());
            if (gpuOptional.isPresent() && gpuOptional.get().getTdpWatts() != null) {
                totalSystemTdp += gpuOptional.get().getTdpWatts();
            }
        }

        // Sum Motherboard TDP
        if (request.motherboardId() != null) {
            var mbOptional = motherboardRepository.findById(request.motherboardId());
            if (mbOptional.isPresent() && mbOptional.get().getTdpWatts() != null) {
                totalSystemTdp += mbOptional.get().getTdpWatts();
            }
        }

        // Sum RAM TDP
        if (request.ramId() != null) {
            var ramOptional = ramRepository.findById(request.ramId());
            if (ramOptional.isPresent() && ramOptional.get().getTdpWatts() != null) {
                totalSystemTdp += ramOptional.get().getTdpWatts();
            }
        }

        // Sum Storage TDP
        if (request.storageId() != null) {
            var storageOptional = storageRepository.findById(request.storageId());
            if (storageOptional.isPresent() && storageOptional.get().getTdpWatts() != null) {
                totalSystemTdp += storageOptional.get().getTdpWatts();
            }
        }

        // Validate whether PSU capacity covers total TDP with 20% safety margin
        return (totalSystemTdp * SAFETY_MARGIN_MULTIPLIER) <= psu.getWattage();
    }

    @Override
    public String getErrorMessage() {
        return "The total system power consumption (with 20% safety margin) exceeds the selected Power Supply Unit (PSU) capacity.";
    }
}
