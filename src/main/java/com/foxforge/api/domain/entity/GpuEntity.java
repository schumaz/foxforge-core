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
@Table(name = "tb_gpu")
public class GpuEntity extends ComponentEntity {

    @Column(nullable = false)
    private Integer vramGb;

    @Column(nullable = false, length = 50)
    private String vramType;

    @Column(nullable = false)
    private Integer lengthMm;

    @Column(nullable = false, length = 100)
    private String powerConnectors;

    @Column(nullable = false)
    private Integer recommendedPsuWattage;

    @Column(nullable = false)
    private Integer gamingScore;

    @Column(nullable = false)
    private Integer workloadScore;

    public GpuEntity(String manufacturer, String model, Double price, Integer tdpWatts,
                     Integer vramGb, String vramType, Integer lengthMm,
                     String powerConnectors, Integer recommendedPsuWattage,
                     Integer gamingScore, Integer workloadScore) {
        super(manufacturer, model, price, tdpWatts);
        this.vramGb = vramGb;
        this.vramType = vramType;
        this.lengthMm = lengthMm;
        this.powerConnectors = powerConnectors;
        this.recommendedPsuWattage = recommendedPsuWattage;
        this.gamingScore = gamingScore;
        this.workloadScore = workloadScore;
    }
}
