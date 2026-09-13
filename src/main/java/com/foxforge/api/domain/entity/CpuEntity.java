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
@Table(name = "tb_cpu")
public class CpuEntity extends ComponentEntity {

    @Column(nullable = false, length = 50)
    private String socket;

    @Column(nullable = false)
    private Integer coreCount;

    @Column(nullable = false)
    private Integer threadCount;

    @Column(nullable = false)
    private Double baseClockGhz;

    @Column(nullable = false)
    private Double boostClockGhz;

    @Column(nullable = false, length = 50)
    private String supportedMemoryType;

    @Column(nullable = false)
    private Integer gamingScore;

    @Column(nullable = false)
    private Integer workloadScore;

    public CpuEntity(String manufacturer, String model, Double price, Integer tdpWatts,
                     String socket, Integer coreCount, Integer threadCount,
                     Double baseClockGhz, Double boostClockGhz, String supportedMemoryType,
                     Integer gamingScore, Integer workloadScore) {
        super(manufacturer, model, price, tdpWatts);
        this.socket = socket;
        this.coreCount = coreCount;
        this.threadCount = threadCount;
        this.baseClockGhz = baseClockGhz;
        this.boostClockGhz = boostClockGhz;
        this.supportedMemoryType = supportedMemoryType;
        this.gamingScore = gamingScore;
        this.workloadScore = workloadScore;
    }
}
