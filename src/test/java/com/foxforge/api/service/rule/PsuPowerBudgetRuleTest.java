package com.foxforge.api.service.rule;

import com.foxforge.api.domain.dto.PcBuildRequest;
import com.foxforge.api.domain.entity.CpuEntity;
import com.foxforge.api.domain.entity.GpuEntity;
import com.foxforge.api.domain.entity.PsuEntity;
import com.foxforge.api.repository.CpuRepository;
import com.foxforge.api.repository.GpuRepository;
import com.foxforge.api.repository.MotherboardRepository;
import com.foxforge.api.repository.PsuRepository;
import com.foxforge.api.repository.RamRepository;
import com.foxforge.api.repository.StorageRepository;
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
class PsuPowerBudgetRuleTest {

    @Mock
    private PsuRepository psuRepository;

    @Mock
    private CpuRepository cpuRepository;

    @Mock
    private GpuRepository gpuRepository;

    @Mock
    private MotherboardRepository motherboardRepository;

    @Mock
    private RamRepository ramRepository;

    @Mock
    private StorageRepository storageRepository;

    @InjectMocks
    private PsuPowerBudgetRule rule;

    @Test
    @DisplayName("Should return true when PSU wattage safely covers total TDP with 20% margin")
    void shouldReturnTrueWhenPsuWattageIsSufficient() {
        // Arrange: CPU (65W) + GPU (200W) = 265W. With 20% margin: 265 * 1.20 = 318W <= 650W PSU
        Long psuId = 1L;
        Long cpuId = 2L;
        Long gpuId = 3L;
        PcBuildRequest request = new PcBuildRequest(cpuId, null, null, gpuId, psuId, null, null, null);

        PsuEntity psu = new PsuEntity("Corsair", "RM650", 100.0, 0, 650, "80+ Gold", "ATX", true, 4, false);
        CpuEntity cpu = new CpuEntity("AMD", "Ryzen 5 5600", 150.0, 65, "AM4", 6, 12, 3.5, 4.4, "DDR4", 80, 75);
        GpuEntity gpu = new GpuEntity("NVIDIA", "RTX 4070", 600.0, 200, 12, "GDDR6X", 242, "1x 8-pin", 650, 85, 80);

        when(psuRepository.findById(psuId)).thenReturn(Optional.of(psu));
        when(cpuRepository.findById(cpuId)).thenReturn(Optional.of(cpu));
        when(gpuRepository.findById(gpuId)).thenReturn(Optional.of(gpu));

        // Act
        boolean result = rule.isSatisfiedBy(request);

        // Assert
        assertTrue(result);
    }

    @Test
    @DisplayName("Should return false when total system TDP exceeds PSU wattage after applying 20% safety margin")
    void shouldReturnFalseWhenPsuWattageIsInsufficient() {
        // Arrange: CPU (250W) + GPU (450W) = 700W. With 20% margin: 700 * 1.20 = 840W > 500W PSU
        Long psuId = 1L;
        Long cpuId = 2L;
        Long gpuId = 3L;
        PcBuildRequest request = new PcBuildRequest(cpuId, null, null, gpuId, psuId, null, null, null);

        PsuEntity psu = new PsuEntity("Generic", "Budget 500", 40.0, 0, 500, "80+ White", "ATX", false, 2, false);
        CpuEntity cpu = new CpuEntity("Intel", "Core i9-13900K", 580.0, 250, "LGA1700", 24, 32, 3.0, 5.8, "DDR5", 98, 97);
        GpuEntity gpu = new GpuEntity("ASUS", "GeForce RTX 4090", 2000.0, 450, 24, "GDDR6X", 358, "1x 16-pin", 1000, 99, 95);

        when(psuRepository.findById(psuId)).thenReturn(Optional.of(psu));
        when(cpuRepository.findById(cpuId)).thenReturn(Optional.of(cpu));
        when(gpuRepository.findById(gpuId)).thenReturn(Optional.of(gpu));

        // Act
        boolean result = rule.isSatisfiedBy(request);

        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("Should return true when PSU ID is null")
    void shouldReturnTrueWhenPsuIdIsNull() {
        // Arrange
        PcBuildRequest request = new PcBuildRequest(1L, null, null, 2L, null, null, null, null);

        // Act & Assert
        assertTrue(rule.isSatisfiedBy(request));
    }
}
