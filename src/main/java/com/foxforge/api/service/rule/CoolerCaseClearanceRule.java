package com.foxforge.api.service.rule;

import com.foxforge.api.domain.dto.PcBuildRequest;
import com.foxforge.api.domain.entity.CaseEntity;
import com.foxforge.api.domain.entity.CoolerEntity;
import com.foxforge.api.repository.CaseRepository;
import com.foxforge.api.repository.CoolerRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

// Rule to validate if the CPU Cooler height fits within the PC Case side panel clearance
@Component
public class CoolerCaseClearanceRule implements CompatibilityRule {

    private final CoolerRepository coolerRepository;
    private final CaseRepository caseRepository;

    // Injects repositories through constructor for Spring IoC
    public CoolerCaseClearanceRule(CoolerRepository coolerRepository, CaseRepository caseRepository) {
        this.coolerRepository = coolerRepository;
        this.caseRepository = caseRepository;
    }

    @Override
    public boolean isSatisfiedBy(PcBuildRequest request) {
        // Skip validation if Cooler or Case is not present (e.g., using stock/boxed cooler or incomplete build)
        if (request.coolerId() == null || request.caseId() == null) {
            return true;
        }

        // Fetch entities from database
        Optional<CoolerEntity> coolerOptional = coolerRepository.findById(request.coolerId());
        Optional<CaseEntity> caseOptional = caseRepository.findById(request.caseId());

        // Incompatible if either component is not found in database
        if (coolerOptional.isEmpty() || caseOptional.isEmpty()) {
            return false;
        }

        CoolerEntity cooler = coolerOptional.get();
        CaseEntity pcCase = caseOptional.get();

        Integer coolerHeight = cooler.getHeightMm();
        Integer maxCoolerHeight = pcCase.getMaxCpuCoolerHeightMm();

        // If dimension metadata is missing, allow it to pass temporarily
        if (coolerHeight == null || maxCoolerHeight == null) {
            return true;
        }

        // CPU Cooler height must not exceed the case maximum cooler clearance
        return coolerHeight <= maxCoolerHeight;
    }

    @Override
    public String getErrorMessage() {
        return "The selected CPU Cooler is physically too tall to fit inside the specified Case, preventing the side panel from closing.";
    }
}
