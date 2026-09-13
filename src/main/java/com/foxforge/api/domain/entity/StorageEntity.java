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
@Table(name = "tb_storage")
public class StorageEntity extends ComponentEntity {

    @Column(nullable = false, length = 50)
    private String storageType;

    @Column(nullable = false)
    private Integer capacityGb;

    @Column(nullable = false)
    private Integer readSpeedMbps;

    @Column(nullable = false)
    private Integer writeSpeedMbps;

    public StorageEntity(String manufacturer, String model, Double price, Integer tdpWatts,
                         String storageType, Integer capacityGb, Integer readSpeedMbps,
                         Integer writeSpeedMbps) {
        super(manufacturer, model, price, tdpWatts);
        this.storageType = storageType;
        this.capacityGb = capacityGb;
        this.readSpeedMbps = readSpeedMbps;
        this.writeSpeedMbps = writeSpeedMbps;
    }
}
