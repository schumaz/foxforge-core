package com.foxforge.api.controller;

import com.foxforge.api.domain.entity.CaseEntity;
import com.foxforge.api.domain.entity.CoolerEntity;
import com.foxforge.api.domain.entity.CpuEntity;
import com.foxforge.api.domain.entity.GpuEntity;
import com.foxforge.api.domain.entity.MotherboardEntity;
import com.foxforge.api.domain.entity.PsuEntity;
import com.foxforge.api.domain.entity.RamEntity;
import com.foxforge.api.repository.CaseRepository;
import com.foxforge.api.repository.CoolerRepository;
import com.foxforge.api.repository.CpuRepository;
import com.foxforge.api.repository.GpuRepository;
import com.foxforge.api.repository.MotherboardRepository;
import com.foxforge.api.repository.PsuRepository;
import com.foxforge.api.repository.RamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PcBuildControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CpuRepository cpuRepository;

    @Autowired
    private MotherboardRepository motherboardRepository;

    @Autowired
    private RamRepository ramRepository;

    @Autowired
    private GpuRepository gpuRepository;

    @Autowired
    private PsuRepository psuRepository;

    @Autowired
    private CaseRepository caseRepository;

    @Autowired
    private CoolerRepository coolerRepository;

    private CpuEntity compatibleCpu;
    private CpuEntity incompatibleCpu;
    private MotherboardEntity motherboard;
    private RamEntity ram;
    private GpuEntity gpu;
    private PsuEntity psu;
    private CaseEntity pcCase;
    private CoolerEntity cooler;

    @BeforeEach
    void setUp() {
        coolerRepository.deleteAll();
        caseRepository.deleteAll();
        psuRepository.deleteAll();
        gpuRepository.deleteAll();
        ramRepository.deleteAll();
        motherboardRepository.deleteAll();
        cpuRepository.deleteAll();

        // AMD AM4 compatible ecosystem
        compatibleCpu = cpuRepository.save(new CpuEntity(
                "AMD", "Ryzen 5 5600", 150.0, 65, "AM4", 6, 12, 3.5, 4.4, "DDR4", 80, 75
        ));

        // Intel LGA1700 CPU for incompatibility testing
        incompatibleCpu = cpuRepository.save(new CpuEntity(
                "Intel", "Core i5-13400F", 200.0, 65, "LGA1700", 10, 16, 2.5, 4.6, "DDR4, DDR5", 85, 82
        ));

        motherboard = motherboardRepository.save(new MotherboardEntity(
                "ASUS", "B550M-PLUS", 110.0, 30, "AM4", "B550", "Micro-ATX", "DDR4", 4, 128, 1, 2
        ));

        ram = ramRepository.save(new RamEntity(
                "Corsair", "Vengeance LPX", 80.0, 15, "DDR4", 16, 2, 3200, 16
        ));

        gpu = gpuRepository.save(new GpuEntity(
                "NVIDIA", "GeForce RTX 4070", 600.0, 200, 12, "GDDR6X", 242, "1x 8-pin", 650, 85, 80
        ));

        psu = psuRepository.save(new PsuEntity(
                "Corsair", "RM650", 100.0, 0, 650, "80+ Gold", "ATX", true, 4, false
        ));

        pcCase = caseRepository.save(new CaseEntity(
                "NZXT", "H510", 90.0, 0, "Mid-Tower", "ATX, Micro-ATX, Mini-ITX", 381, 165, 200
        ));

        cooler = coolerRepository.save(new CoolerEntity(
                "DeepCool", "AK400", 35.0, 0, "Air", 220, 155, "AM4, AM5, LGA1700"
        ));
    }

    @Test
    @DisplayName("POST /api/builds/validate - Should return HTTP 200 OK for a completely compatible build")
    void shouldReturn200WhenBuildIsCompatible() throws Exception {
        // Arrange
        String requestJson = """
                {
                    "cpuId": %d,
                    "motherboardId": %d,
                    "ramId": %d,
                    "gpuId": %d,
                    "psuId": %d,
                    "caseId": %d,
                    "coolerId": %d
                }
                """.formatted(
                compatibleCpu.getId(),
                motherboard.getId(),
                ram.getId(),
                gpu.getId(),
                psu.getId(),
                pcCase.getId(),
                cooler.getId()
        );

        // Act & Assert
        mockMvc.perform(post("/api/builds/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Build configuration is compatible!"));
    }

    @Test
    @DisplayName("POST /api/builds/validate - Should return HTTP 400 Bad Request when physical rule is violated")
    void shouldReturn400WhenBuildIsIncompatible() throws Exception {
        // Arrange: Incompatible Intel LGA1700 CPU on AMD AM4 Motherboard
        String requestJson = """
                {
                    "cpuId": %d,
                    "motherboardId": %d,
                    "ramId": %d,
                    "gpuId": %d,
                    "psuId": %d,
                    "caseId": %d,
                    "coolerId": %d
                }
                """.formatted(
                incompatibleCpu.getId(),
                motherboard.getId(),
                ram.getId(),
                gpu.getId(),
                psu.getId(),
                pcCase.getId(),
                cooler.getId()
        );

        // Act & Assert
        mockMvc.perform(post("/api/builds/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }
}
