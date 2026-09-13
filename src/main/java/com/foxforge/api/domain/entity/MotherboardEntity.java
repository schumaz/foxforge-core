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
@Table(name = "tb_motherboard")
public class MotherboardEntity extends ComponentEntity {

    @Column(nullable = false, length = 50)
    private String socket;

    @Column(nullable = false, length = 50)
    private String chipset;

    @Column(nullable = false, length = 50)
    private String formFactor;

    @Column(nullable = false, length = 50)
    private String memoryType;

    @Column(nullable = false)
    private Integer memorySlots;

    @Column(nullable = false)
    private Integer maxMemoryCapacityGb;

    @Column(nullable = false)
    private Integer pcieSlots;

    @Column(nullable = false)
    private Integer m2Slots;

    public MotherboardEntity(String manufacturer, String model, Double price, Integer tdpWatts,
                             String socket, String chipset, String formFactor,
                             String memoryType, Integer memorySlots, Integer maxMemoryCapacityGb,
                             Integer pcieSlots, Integer m2Slots) {
        super(manufacturer, model, price, tdpWatts);
        this.socket = socket;
        this.chipset = chipset;
        this.formFactor = formFactor;
        this.memoryType = memoryType;
        this.memorySlots = memorySlots;
        this.maxMemoryCapacityGb = maxMemoryCapacityGb;
        this.pcieSlots = pcieSlots;
        this.m2Slots = m2Slots;
    }
}
