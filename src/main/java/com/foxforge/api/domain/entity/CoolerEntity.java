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
@Table(name = "tb_cooler")
public class CoolerEntity extends ComponentEntity {

    @Column(nullable = false, length = 50)
    private String coolerType;

    @Column(nullable = false)
    private Integer maxTdpWatts;

    @Column(nullable = false)
    private Integer heightMm;

    @Column(nullable = false, length = 150)
    private String supportedSockets;

    public CoolerEntity(String manufacturer, String model, Double price, Integer tdpWatts,
                        String coolerType, Integer maxTdpWatts, Integer heightMm,
                        String supportedSockets) {
        super(manufacturer, model, price, tdpWatts);
        this.coolerType = coolerType;
        this.maxTdpWatts = maxTdpWatts;
        this.heightMm = heightMm;
        this.supportedSockets = supportedSockets;
    }
}
