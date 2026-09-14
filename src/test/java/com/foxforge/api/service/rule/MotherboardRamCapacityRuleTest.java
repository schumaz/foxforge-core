package com.foxforge.api.service.rule;

import com.foxforge.api.domain.dto.PcBuildRequest;
import com.foxforge.api.domain.entity.MotherboardEntity;
import com.foxforge.api.domain.entity.RamEntity;
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
class MotherboardRamCapacityRuleTest {

    @Mock
    private MotherboardRepository motherboardRepository;

    @Mock
    private RamRepository ramRepository;

    @InjectMocks
    private MotherboardRamCapacityRule rule;

    @Test
    @DisplayName("Should return true when RAM kit modules and capacity fit within Motherboard limits")
    void shouldReturnTrueWhenRamFitsMotherboardLimits() {
        // Arrange: 2 modules <= 4 slots, 32GB <= 128GB max capacity
        Long motherboardId = 1L;
        Long ramId = 2L;
        PcBuildRequest request = new PcBuildRequest(null, motherboardId, ramId, null, null, null, null, null);

        MotherboardEntity motherboard = new MotherboardEntity("ASUS", "B550M", 110.0, 30, "AM4", "B550", "Micro-ATX", "DDR4", 4, 128, 1, 2);
        RamEntity ram = new RamEntity("Corsair", "Vengeance", 80.0, 15, "DDR4", 32, 2, 3200, 16);

        when(motherboardRepository.findById(motherboardId)).thenReturn(Optional.of(motherboard));
        when(ramRepository.findById(ramId)).thenReturn(Optional.of(ram));

        // Act
        boolean result = rule.isSatisfiedBy(request);

        // Assert
        assertTrue(result);
    }

    @Test
    @DisplayName("Should return false when RAM module count exceeds available motherboard slots")
    void shouldReturnFalseWhenModulesExceedSlots() {
        // Arrange: 4 modules > 2 slots
        Long motherboardId = 1L;
        Long ramId = 2L;
        PcBuildRequest request = new PcBuildRequest(null, motherboardId, ramId, null, null, null, null, null);

        MotherboardEntity motherboard = new MotherboardEntity("Gigabyte", "A320M", 60.0, 25, "AM4", "A320", "Micro-ATX", "DDR4", 2, 64, 1, 1);
        RamEntity ram = new RamEntity("Corsair", "Dominator Quad", 200.0, 20, "DDR4", 64, 4, 3200, 16);

        when(motherboardRepository.findById(motherboardId)).thenReturn(Optional.of(motherboard));
        when(ramRepository.findById(ramId)).thenReturn(Optional.of(ram));

        // Act
        boolean result = rule.isSatisfiedBy(request);

        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("Should return false when RAM total capacity in GB exceeds maximum motherboard capacity")
    void shouldReturnFalseWhenCapacityExceedsMaxMbCapacity() {
        // Arrange: 128GB capacity > 64GB max capacity
        Long motherboardId = 1L;
        Long ramId = 2L;
        PcBuildRequest request = new PcBuildRequest(null, motherboardId, ramId, null, null, null, null, null);

        MotherboardEntity motherboard = new MotherboardEntity("Gigabyte", "A320M", 60.0, 25, "AM4", "A320", "Micro-ATX", "DDR4", 2, 64, 1, 1);
        RamEntity ram = new RamEntity("G.Skill", "Ripjaws Dual", 350.0, 20, "DDR4", 128, 2, 3200, 16);

        when(motherboardRepository.findById(motherboardId)).thenReturn(Optional.of(motherboard));
        when(ramRepository.findById(ramId)).thenReturn(Optional.of(ram));

        // Act
        boolean result = rule.isSatisfiedBy(request);

        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("Should return true when Motherboard or RAM ID is null")
    void shouldReturnTrueWhenIdIsNull() {
        // Arrange
        PcBuildRequest requestWithNullMb = new PcBuildRequest(null, null, 2L, null, null, null, null, null);
        PcBuildRequest requestWithNullRam = new PcBuildRequest(null, 1L, null, null, null, null, null, null);

        // Act & Assert
        assertTrue(rule.isSatisfiedBy(requestWithNullMb));
        assertTrue(rule.isSatisfiedBy(requestWithNullRam));
    }
}
