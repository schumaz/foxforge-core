package com.foxforge.api.service.rule;

import com.foxforge.api.domain.dto.PcBuildRequest;
import com.foxforge.api.domain.entity.CpuEntity;
import com.foxforge.api.domain.entity.MotherboardEntity;
import com.foxforge.api.repository.CpuRepository;
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
class CpuMotherboardSocketRuleTest {

    @Mock
    private CpuRepository cpuRepository;

    @Mock
    private MotherboardRepository motherboardRepository;

    @InjectMocks
    private CpuMotherboardSocketRule rule;

    @Test
    @DisplayName("Should return true when CPU socket matches Motherboard socket")
    void shouldReturnTrueWhenSocketsMatch() {
        // Arrange
        Long cpuId = 1L;
        Long motherboardId = 2L;
        PcBuildRequest request = new PcBuildRequest(cpuId, motherboardId, null, null, null, null, null, null);

        CpuEntity cpu = new CpuEntity("AMD", "Ryzen 5 5600", 150.0, 65, "AM4", 6, 12, 3.5, 4.4, "DDR4", 80, 75);
        MotherboardEntity motherboard = new MotherboardEntity("ASUS", "B550M", 110.0, 30, "AM4", "B550", "Micro-ATX", "DDR4", 4, 128, 1, 2);

        when(cpuRepository.findById(cpuId)).thenReturn(Optional.of(cpu));
        when(motherboardRepository.findById(motherboardId)).thenReturn(Optional.of(motherboard));

        // Act
        boolean result = rule.isSatisfiedBy(request);

        // Assert
        assertTrue(result);
    }

    @Test
    @DisplayName("Should return false when CPU socket differs from Motherboard socket")
    void shouldReturnFalseWhenSocketsDoNotMatch() {
        // Arrange
        Long cpuId = 1L;
        Long motherboardId = 2L;
        PcBuildRequest request = new PcBuildRequest(cpuId, motherboardId, null, null, null, null, null, null);

        CpuEntity cpu = new CpuEntity("Intel", "Core i5-13400F", 200.0, 65, "LGA1700", 10, 16, 2.5, 4.6, "DDR4, DDR5", 85, 82);
        MotherboardEntity motherboard = new MotherboardEntity("ASUS", "B550M", 110.0, 30, "AM4", "B550", "Micro-ATX", "DDR4", 4, 128, 1, 2);

        when(cpuRepository.findById(cpuId)).thenReturn(Optional.of(cpu));
        when(motherboardRepository.findById(motherboardId)).thenReturn(Optional.of(motherboard));

        // Act
        boolean result = rule.isSatisfiedBy(request);

        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("Should return true when CPU or Motherboard ID is null (partial build)")
    void shouldReturnTrueWhenIdIsNull() {
        // Arrange
        PcBuildRequest requestWithNullCpu = new PcBuildRequest(null, 2L, null, null, null, null, null, null);
        PcBuildRequest requestWithNullMb = new PcBuildRequest(1L, null, null, null, null, null, null, null);

        // Act & Assert
        assertTrue(rule.isSatisfiedBy(requestWithNullCpu));
        assertTrue(rule.isSatisfiedBy(requestWithNullMb));
    }

    @Test
    @DisplayName("Should return false when entity is not found in database")
    void shouldReturnFalseWhenEntityNotFound() {
        // Arrange
        Long cpuId = 1L;
        Long motherboardId = 2L;
        PcBuildRequest request = new PcBuildRequest(cpuId, motherboardId, null, null, null, null, null, null);

        when(cpuRepository.findById(cpuId)).thenReturn(Optional.empty());

        // Act
        boolean result = rule.isSatisfiedBy(request);

        // Assert
        assertFalse(result);
    }
}
