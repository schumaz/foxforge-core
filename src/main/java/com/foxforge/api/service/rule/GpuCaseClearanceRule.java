package com.foxforge.api.service.rule;

import com.foxforge.api.domain.dto.PcBuildRequest;
import com.foxforge.api.domain.entity.CaseEntity;
import com.foxforge.api.domain.entity.GpuEntity;
import com.foxforge.api.repository.CaseRepository;
import com.foxforge.api.repository.GpuRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

// Rule to validate if the GPU physical length fits within the chosen Case internal dimensions
@Component
public class GpuCaseClearanceRule implements CompatibilityRule {

    private final GpuRepository gpuRepository;
    private final CaseRepository caseRepository;

    // Injects repositories through constructor for Spring IoC
    public GpuCaseClearanceRule(GpuRepository gpuRepository, CaseRepository caseRepository) {
        this.gpuRepository = gpuRepository;
        this.caseRepository = caseRepository;
    }

    @Override
    public boolean isSatisfiedBy(PcBuildRequest request) {
        // Skip validation if GPU or Case is not present in the request (e.g., builds with integrated graphics)
        if (request.gpuId() == null || request.caseId() == null) {
            return true;
        }

        // Fetch entities from database
        Optional<GpuEntity> gpuOptional = gpuRepository.findById(request.gpuId());
        Optional<CaseEntity> caseOptional = caseRepository.findById(request.caseId());

        // Incompatible if either component is not found in database
        if (gpuOptional.isEmpty() || caseOptional.isEmpty()) {
            return false;
        }

        GpuEntity gpu = gpuOptional.get();
        CaseEntity pcCase = caseOptional.get();

        Integer gpuLength = gpu.getLengthMm();
        Integer maxGpuLength = pcCase.getMaxGpuLengthMm();

        // If dimensions are missing from records, consider compatible by default
        if (gpuLength == null || maxGpuLength == null) {
            return true;
        }

        // GPU must be shorter than or equal to maximum supported clearance in the case
        return gpuLength <= maxGpuLength;
    }

    @Override
    public String getErrorMessage() {
        return "The chosen GPU is too long to fit inside the selected Case boundaries.";
    }
}
