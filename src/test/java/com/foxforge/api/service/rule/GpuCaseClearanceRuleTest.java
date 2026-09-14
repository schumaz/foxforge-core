package com.foxforge.api.service.rule;

import com.foxforge.api.domain.dto.PcBuildRequest;
import com.foxforge.api.domain.entity.CaseEntity;
import com.foxforge.api.domain.entity.GpuEntity;
import com.foxforge.api.repository.CaseRepository;
import com.foxforge.api.repository.GpuRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GpuCaseClearanceRuleTest {

    @Mock
    private GpuRepository gpuRepository;

    @Mock
    private CaseRepository caseRepository;

    @InjectMocks
    private GpuCaseClearanceRule rule;

    @Test
    @DisplayName("Should return true when GPU length fits within Case max clearance")
    void shouldReturnTrueWhenGpuFitsInCase() {
        // Arrange
        Long gpuId = 1L;
        Long caseId = 2L;
        PcBuildRequest request = new PcBuildRequest(null, null, null, gpuId, null, null, caseId, null);

        GpuEntity gpu = new GpuEntity("NVIDIA", "GeForce RTX 4070", 600.0, 200, 12, "GDDR6X", 242, "1x 8-pin", 650, 85, 80);
        CaseEntity pcCase = new CaseEntity("NZXT", "H510", 90.0, 0, "Mid-Tower", "ATX, Micro-ATX, Mini-ITX", 381, 165, 200);

        when(gpuRepository.findById(gpuId)).thenReturn(Optional.of(gpu));
        when(caseRepository.findById(caseId)).thenReturn(Optional.of(pcCase));

        // Act
        boolean result = rule.isSatisfiedBy(request);

        // Assert
        assertTrue(result);
    }

    @Test
    @DisplayName("Should return false when GPU is physically longer than Case clearance")
    void shouldReturnFalseWhenGpuExceedsCaseClearance() {
        // Arrange
        Long gpuId = 1L;
        Long caseId = 2L;
        PcBuildRequest request = new PcBuildRequest(null, null, null, gpuId, null, null, caseId, null);

        GpuEntity gpu = new GpuEntity("ASUS", "GeForce RTX 4090 ROG Strix", 2000.0, 450, 24, "GDDR6X", 358, "1x 16-pin", 1000, 99, 95);
        CaseEntity pcCase = new CaseEntity("Cooler Master", "NR200P", 100.0, 0, "Small Form Factor", "Mini-ITX", 330, 155, 130);

        when(gpuRepository.findById(gpuId)).thenReturn(Optional.of(gpu));
        when(caseRepository.findById(caseId)).thenReturn(Optional.of(pcCase));

        // Act
        boolean result = rule.isSatisfiedBy(request);

        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("Should return true when GPU ID is null (e.g., build with integrated graphics)")
    void shouldReturnTrueWhenGpuIdIsNull() {
        // Arrange
        PcBuildRequest request = new PcBuildRequest(null, null, null, null, null, null, 1L, null);

        // Act & Assert
        assertTrue(rule.isSatisfiedBy(request));
    }
}
