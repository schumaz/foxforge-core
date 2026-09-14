package com.foxforge.api.service.rule;

import com.foxforge.api.domain.dto.PcBuildRequest;
import com.foxforge.api.domain.entity.CpuEntity;
import com.foxforge.api.domain.entity.MotherboardEntity;
import com.foxforge.api.domain.entity.RamEntity;
import com.foxforge.api.repository.CpuRepository;
import com.foxforge.api.repository.MotherboardRepository;
import com.foxforge.api.repository.RamRepository;
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
class MemoryMotherboardGenerationRuleTest {

    @Mock
    private RamRepository ramRepository;

    @Mock
    private MotherboardRepository motherboardRepository;

    @Mock
    private CpuRepository cpuRepository;

    @InjectMocks
    private MemoryMotherboardGenerationRule rule;

    @Test
    @DisplayName("Should return true when RAM generation matches Motherboard and CPU support")
    void shouldReturnTrueWhenRamGenerationMatches() {
        // Arrange
        Long ramId = 1L;
        Long motherboardId = 2L;
        Long cpuId = 3L;
        PcBuildRequest request = new PcBuildRequest(cpuId, motherboardId, ramId, null, null, null, null, null);

        RamEntity ram = new RamEntity("Corsair", "Vengeance", 80.0, 15, "DDR4", 16, 2, 3200, 16);
        MotherboardEntity motherboard = new MotherboardEntity("ASUS", "B550M", 110.0, 30, "AM4", "B550", "Micro-ATX", "DDR4", 4, 128, 1, 2);
        CpuEntity cpu = new CpuEntity("AMD", "Ryzen 5 5600", 150.0, 65, "AM4", 6, 12, 3.5, 4.4, "DDR4", 80, 75);

        when(ramRepository.findById(ramId)).thenReturn(Optional.of(ram));
        when(motherboardRepository.findById(motherboardId)).thenReturn(Optional.of(motherboard));
        when(cpuRepository.findById(cpuId)).thenReturn(Optional.of(cpu));

        // Act
        boolean result = rule.isSatisfiedBy(request);

        // Assert
        assertTrue(result);
    }

    @Test
    @DisplayName("Should return false when RAM generation conflicts with Motherboard memory type")
    void shouldReturnFalseWhenRamConflictsWithMotherboard() {
        // Arrange
        Long ramId = 1L;
        Long motherboardId = 2L;
        Long cpuId = 3L;
        PcBuildRequest request = new PcBuildRequest(cpuId, motherboardId, ramId, null, null, null, null, null);

        RamEntity ram = new RamEntity("Kingston", "Fury Beast", 120.0, 15, "DDR5", 32, 2, 6000, 36);
        MotherboardEntity motherboard = new MotherboardEntity("ASUS", "B550M", 110.0, 30, "AM4", "B550", "Micro-ATX", "DDR4", 4, 128, 1, 2);
        CpuEntity cpu = new CpuEntity("Intel", "Core i5-13400F", 200.0, 65, "LGA1700", 10, 16, 2.5, 4.6, "DDR4, DDR5", 85, 82);

        when(ramRepository.findById(ramId)).thenReturn(Optional.of(ram));
        when(motherboardRepository.findById(motherboardId)).thenReturn(Optional.of(motherboard));
        when(cpuRepository.findById(cpuId)).thenReturn(Optional.of(cpu));

        // Act
        boolean result = rule.isSatisfiedBy(request);

        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("Should return false when RAM generation is not supported by CPU")
    void shouldReturnFalseWhenRamNotSupportedByCpu() {
        // Arrange
        Long ramId = 1L;
        Long motherboardId = 2L;
        Long cpuId = 3L;
        PcBuildRequest request = new PcBuildRequest(cpuId, motherboardId, ramId, null, null, null, null, null);

        RamEntity ram = new RamEntity("Kingston", "Fury Beast", 120.0, 15, "DDR5", 32, 2, 6000, 36);
        MotherboardEntity motherboard = new MotherboardEntity("MSI", "B760M DDR5", 130.0, 30, "LGA1700", "B760", "Micro-ATX", "DDR5", 4, 128, 1, 2);
        CpuEntity cpu = new CpuEntity("AMD", "Ryzen 5 5600", 150.0, 65, "AM4", 6, 12, 3.5, 4.4, "DDR4", 80, 75);

        when(ramRepository.findById(ramId)).thenReturn(Optional.of(ram));
        when(motherboardRepository.findById(motherboardId)).thenReturn(Optional.of(motherboard));
        when(cpuRepository.findById(cpuId)).thenReturn(Optional.of(cpu));

        // Act
        boolean result = rule.isSatisfiedBy(request);

        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("Should return true when any ID is missing")
    void shouldReturnTrueWhenIdIsNull() {
        // Arrange
        PcBuildRequest request = new PcBuildRequest(null, 1L, 2L, null, null, null, null, null);

        // Act & Assert
        assertTrue(rule.isSatisfiedBy(request));
    }
}
