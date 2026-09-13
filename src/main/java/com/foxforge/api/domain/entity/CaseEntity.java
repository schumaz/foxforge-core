package com.foxforge.api.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tb_case")
public class CaseEntity extends ComponentEntity {

    @Column(nullable = false, length = 50)
    private String formFactor;

    @Column(nullable = false, length = 100)
    private String supportedMotherboardFormats;

    @Column(nullable = false)
    private Integer maxGpuLengthMm;

    @Column(nullable = false)
    private Integer maxCpuCoolerHeightMm;

    @Column(nullable = false)
    private Integer maxPsuLengthMm;

    public CaseEntity(String manufacturer, String model, Double price, Integer tdpWatts,
                      String formFactor, String supportedMotherboardFormats,
                      Integer maxGpuLengthMm, Integer maxCpuCoolerHeightMm, Integer maxPsuLengthMm) {
        super(manufacturer, model, price, tdpWatts);
        this.formFactor = formFactor;
        this.supportedMotherboardFormats = supportedMotherboardFormats;
        this.maxGpuLengthMm = maxGpuLengthMm;
        this.maxCpuCoolerHeightMm = maxCpuCoolerHeightMm;
        this.maxPsuLengthMm = maxPsuLengthMm;
    }
}
