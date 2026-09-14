package com.foxforge.api.service.rule;

import com.foxforge.api.domain.dto.PcBuildRequest;
import com.foxforge.api.domain.entity.CaseEntity;
import com.foxforge.api.domain.entity.MotherboardEntity;
import com.foxforge.api.repository.CaseRepository;
import com.foxforge.api.repository.MotherboardRepository;
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
class CaseMotherboardDimensionRuleTest {

    @Mock
    private CaseRepository caseRepository;

    @Mock
    private MotherboardRepository motherboardRepository;

    @InjectMocks
    private CaseMotherboardDimensionRule rule;

    @Test
    @DisplayName("Should return true when Motherboard form factor is supported by Case")
    void shouldReturnTrueWhenMotherboardFitsInCase() {
        // Arrange
        Long caseId = 1L;
        Long motherboardId = 2L;
        PcBuildRequest request = new PcBuildRequest(null, motherboardId, null, null, null, null, caseId, null);

        CaseEntity pcCase = new CaseEntity("NZXT", "H510", 90.0, 0, "Mid-Tower", "ATX, Micro-ATX, Mini-ITX", 381, 165, 200);
        MotherboardEntity motherboard = new MotherboardEntity("ASUS", "B550M", 110.0, 30, "AM4", "B550", "Micro-ATX", "DDR4", 4, 128, 1, 2);

        when(caseRepository.findById(caseId)).thenReturn(Optional.of(pcCase));
        when(motherboardRepository.findById(motherboardId)).thenReturn(Optional.of(motherboard));

        // Act
        boolean result = rule.isSatisfiedBy(request);

        // Assert
        assertTrue(result);
    }

    @Test
    @DisplayName("Should return false when Motherboard form factor is physically incompatible with Case")
    void shouldReturnFalseWhenMotherboardTooLargeForCase() {
        // Arrange
        Long caseId = 1L;
        Long motherboardId = 2L;
        PcBuildRequest request = new PcBuildRequest(null, motherboardId, null, null, null, null, caseId, null);

        CaseEntity pcCase = new CaseEntity("Cooler Master", "NR200P", 100.0, 0, "Small Form Factor", "Mini-ITX", 330, 155, 130);
        MotherboardEntity motherboard = new MotherboardEntity("MSI", "MAG B650 TOMAHAWK", 200.0, 35, "AM5", "B650", "ATX", "DDR5", 4, 128, 2, 3);

        when(caseRepository.findById(caseId)).thenReturn(Optional.of(pcCase));
        when(motherboardRepository.findById(motherboardId)).thenReturn(Optional.of(motherboard));

        // Act
        boolean result = rule.isSatisfiedBy(request);

        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("Should return true when Case ID or Motherboard ID is null")
    void shouldReturnTrueWhenIdIsNull() {
        // Arrange
        PcBuildRequest requestWithNullCase = new PcBuildRequest(null, 2L, null, null, null, null, null, null);
        PcBuildRequest requestWithNullMb = new PcBuildRequest(null, null, null, null, null, null, 1L, null);

        // Act & Assert
        assertTrue(rule.isSatisfiedBy(requestWithNullCase));
        assertTrue(rule.isSatisfiedBy(requestWithNullMb));
    }
}
