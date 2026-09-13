package com.foxforge.api.config;

import com.foxforge.api.domain.entity.CaseEntity;
import com.foxforge.api.domain.entity.CoolerEntity;
import com.foxforge.api.domain.entity.CpuEntity;
import com.foxforge.api.domain.entity.GpuEntity;
import com.foxforge.api.domain.entity.MotherboardEntity;
import com.foxforge.api.domain.entity.PsuEntity;
import com.foxforge.api.domain.entity.RamEntity;
import com.foxforge.api.domain.entity.StorageEntity;
import com.foxforge.api.repository.CaseRepository;
import com.foxforge.api.repository.CoolerRepository;
import com.foxforge.api.repository.CpuRepository;
import com.foxforge.api.repository.GpuRepository;
import com.foxforge.api.repository.MotherboardRepository;
import com.foxforge.api.repository.PsuRepository;
import com.foxforge.api.repository.RamRepository;
import com.foxforge.api.repository.StorageRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("dev")
public class DatabaseSeeder implements CommandLineRunner {

    private final CpuRepository cpuRepository;
    private final MotherboardRepository motherboardRepository;
    private final GpuRepository gpuRepository;
    private final RamRepository ramRepository;
    private final PsuRepository psuRepository;
    private final CaseRepository caseRepository;
    private final CoolerRepository coolerRepository;
    private final StorageRepository storageRepository;

    public DatabaseSeeder(CpuRepository cpuRepository,
                          MotherboardRepository motherboardRepository,
                          GpuRepository gpuRepository,
                          RamRepository ramRepository,
                          PsuRepository psuRepository,
                          CaseRepository caseRepository,
                          CoolerRepository coolerRepository,
                          StorageRepository storageRepository) {
        this.cpuRepository = cpuRepository;
        this.motherboardRepository = motherboardRepository;
        this.gpuRepository = gpuRepository;
        this.ramRepository = ramRepository;
        this.psuRepository = psuRepository;
        this.caseRepository = caseRepository;
        this.coolerRepository = coolerRepository;
        this.storageRepository = storageRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (cpuRepository.count() > 0) {
            return; // Skip seeding if database is already populated
        }

        // 1. Processors (CPUs)
        CpuEntity ryzen5600x = new CpuEntity(
                "AMD", "Ryzen 5 5600X", 159.99, 65,
                "AM4", 6, 12,
                3.7, 4.6, "DDR4",
                7500, 6800
        );

        CpuEntity corei5_13400f = new CpuEntity(
                "Intel", "Core i5-13400F", 209.99, 65,
                "LGA1700", 10, 16,
                2.5, 4.6, "DDR4, DDR5",
                8100, 8500
        );
        cpuRepository.saveAll(List.of(ryzen5600x, corei5_13400f));

        // 2. Motherboards
        MotherboardEntity gigabyteB550 = new MotherboardEntity(
                "Gigabyte", "B550 Gaming X V2", 119.99, 15,
                "AM4", "B550", "ATX",
                "DDR4", 4, 128,
                2, 2
        );

        MotherboardEntity msiB760m = new MotherboardEntity(
                "MSI", "PRO B760M-P", 99.99, 15,
                "LGA1700", "B760", "Micro-ATX",
                "DDR5", 4, 192,
                1, 2
        );
        motherboardRepository.saveAll(List.of(gigabyteB550, msiB760m));

        // 3. Graphics Cards (GPUs)
        GpuEntity msirxt4060 = new GpuEntity(
                "MSI", "GeForce RTX 4060 Ventus 2X Black", 299.99, 115,
                8, "GDDR6", 199,
                "1x 8-pin", 550,
                12000, 9500
        );

        GpuEntity rx7600 = new GpuEntity(
                "XFX", "Speedster SWFT 210 Radeon RX 7600", 259.99, 165,
                8, "GDDR6", 241,
                "1x 8-pin", 550,
                11500, 9000
        );
        gpuRepository.saveAll(List.of(msirxt4060, rx7600));

        // 4. Memory (RAM)
        RamEntity corsairVengeanceDdr4 = new RamEntity(
                "Corsair", "Vengeance LPX 16GB (2x8GB) DDR4", 44.99, 5,
                "DDR4", 16, 2,
                3200, 16
        );

        RamEntity kingstonFuryDdr5 = new RamEntity(
                "Kingston", "FURY Beast 32GB (2x16GB) DDR5", 109.99, 8,
                "DDR5", 32, 2,
                6000, 36
        );
        ramRepository.saveAll(List.of(corsairVengeanceDdr4, kingstonFuryDdr5));

        // 5. Power Supplies (PSU)
        PsuEntity corsairRm650x = new PsuEntity(
                "Corsair", "RM650x (2021)", 104.99, 0,
                650, "80 Plus Gold", "ATX",
                true, 4, false
        );
        psuRepository.save(corsairRm650x);

        // 6. Cases (Chassis)
        CaseEntity nzxtH5Flow = new CaseEntity(
                "NZXT", "H5 Flow", 94.99, 0,
                "ATX Mid Tower", "ATX, Micro-ATX, Mini-ITX",
                365, 165, 200
        );
        caseRepository.save(nzxtH5Flow);

        // 7. CPU Coolers
        CoolerEntity deepcoolAk400 = new CoolerEntity(
                "DeepCool", "AK400", 34.99, 5,
                "Air Cooler", 220, 155,
                "AM4, AM5, LGA1700, LGA1200"
        );
        coolerRepository.save(deepcoolAk400);

        // 8. Storage Drives
        StorageEntity kingstonNv2 = new StorageEntity(
                "Kingston", "NV2 1TB M.2 NVMe", 59.99, 4,
                "NVMe SSD", 1000, 3500,
                2100
        );
        storageRepository.save(kingstonNv2);
    }
}
