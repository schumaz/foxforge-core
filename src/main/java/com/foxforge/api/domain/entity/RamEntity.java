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
@Table(name = "tb_ram")
public class RamEntity extends ComponentEntity {

    @Column(nullable = false, length = 50)
    private String memoryType;

    @Column(nullable = false)
    private Integer capacityGb;

    @Column(nullable = false)
    private Integer modulesCount;

    @Column(nullable = false)
    private Integer speedMhz;

    @Column(nullable = false)
    private Integer casLatency;

    public RamEntity(String manufacturer, String model, Double price, Integer tdpWatts,
                     String memoryType, Integer capacityGb, Integer modulesCount,
                     Integer speedMhz, Integer casLatency) {
        super(manufacturer, model, price, tdpWatts);
        this.memoryType = memoryType;
        this.capacityGb = capacityGb;
        this.modulesCount = modulesCount;
        this.speedMhz = speedMhz;
        this.casLatency = casLatency;
    }
}
