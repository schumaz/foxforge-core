package com.foxforge.api.service.rule;

import com.foxforge.api.domain.dto.PcBuildRequest;
import com.foxforge.api.domain.entity.CaseEntity;
import com.foxforge.api.domain.entity.CoolerEntity;
import com.foxforge.api.repository.CaseRepository;
import com.foxforge.api.repository.CoolerRepository;
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
class CoolerCaseClearanceRuleTest {

    @Mock
    private CoolerRepository coolerRepository;

    @Mock
    private CaseRepository caseRepository;

    @InjectMocks
    private CoolerCaseClearanceRule rule;

    @Test
    @DisplayName("Should return true when Cooler height fits within Case max cooler clearance")
    void shouldReturnTrueWhenCoolerFitsInCase() {
        // Arrange
        Long coolerId = 1L;
        Long caseId = 2L;
        PcBuildRequest request = new PcBuildRequest(null, null, null, null, null, null, caseId, coolerId);

        CoolerEntity cooler = new CoolerEntity("DeepCool", "AK400", 35.0, 0, "Air", 220, 155, "AM4, AM5, LGA1700");
        CaseEntity pcCase = new CaseEntity("NZXT", "H510", 90.0, 0, "Mid-Tower", "ATX, Micro-ATX, Mini-ITX", 381, 165, 200);

        when(coolerRepository.findById(coolerId)).thenReturn(Optional.of(cooler));
        when(caseRepository.findById(caseId)).thenReturn(Optional.of(pcCase));

        // Act
        boolean result = rule.isSatisfiedBy(request);

        // Assert
        assertTrue(result);
    }

    @Test
    @DisplayName("Should return false when Cooler height exceeds Case max clearance")
    void shouldReturnFalseWhenCoolerExceedsCaseClearance() {
        // Arrange
        Long coolerId = 1L;
        Long caseId = 2L;
        PcBuildRequest request = new PcBuildRequest(null, null, null, null, null, null, caseId, coolerId);

        CoolerEntity cooler = new CoolerEntity("Noctua", "NH-D15", 110.0, 0, "Air", 250, 165, "AM4, AM5, LGA1700");
        CaseEntity pcCase = new CaseEntity("Cooler Master", "NR200P", 100.0, 0, "Small Form Factor", "Mini-ITX", 330, 155, 130);

        when(coolerRepository.findById(coolerId)).thenReturn(Optional.of(cooler));
        when(caseRepository.findById(caseId)).thenReturn(Optional.of(pcCase));

        // Act
        boolean result = rule.isSatisfiedBy(request);

        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("Should return true when Cooler ID is null (e.g., using stock/boxed cooler)")
    void shouldReturnTrueWhenCoolerIdIsNull() {
        // Arrange
        PcBuildRequest request = new PcBuildRequest(null, null, null, null, null, null, 1L, null);

        // Act & Assert
        assertTrue(rule.isSatisfiedBy(request));
    }
}
