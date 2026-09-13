package com.foxforge.api.service.rule;

import com.foxforge.api.domain.dto.PcBuildRequest;
import com.foxforge.api.domain.entity.CaseEntity;
import com.foxforge.api.domain.entity.MotherboardEntity;
import com.foxforge.api.repository.CaseRepository;
import com.foxforge.api.repository.MotherboardRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

// Rule to validate if the Motherboard form factor fits into the chosen PC Case
@Component
public class CaseMotherboardDimensionRule implements CompatibilityRule {

    private final CaseRepository caseRepository;
    private final MotherboardRepository motherboardRepository;

    // Injects repositories through constructor for Spring IoC
    public CaseMotherboardDimensionRule(CaseRepository caseRepository, MotherboardRepository motherboardRepository) {
        this.caseRepository = caseRepository;
        this.motherboardRepository = motherboardRepository;
    }

    @Override
    public boolean isSatisfiedBy(PcBuildRequest request) {
        // Skip validation if any relevant component ID is missing
        if (request.caseId() == null || request.motherboardId() == null) {
            return true;
        }

        // Fetch entities from database
        Optional<CaseEntity> caseOptional = caseRepository.findById(request.caseId());
        Optional<MotherboardEntity> motherboardOptional = motherboardRepository.findById(request.motherboardId());

        // Incompatible if either component is not found in database
        if (caseOptional.isEmpty() || motherboardOptional.isEmpty()) {
            return false;
        }

        CaseEntity pcCase = caseOptional.get();
        MotherboardEntity motherboard = motherboardOptional.get();

        String supportedFormats = pcCase.getSupportedMotherboardFormats();
        String motherboardFormFactor = motherboard.getFormFactor();

        // Check whether the case supports the motherboard's form factor (case-insensitive check)
        return supportedFormats != null &&
                motherboardFormFactor != null &&
                supportedFormats.toUpperCase().contains(motherboardFormFactor.toUpperCase());
    }

    @Override
    public String getErrorMessage() {
        return "The selected Motherboard form factor is physically too large or not supported by the chosen Case.";
    }
}
