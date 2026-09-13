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
@Table(name = "tb_psu")
public class PsuEntity extends ComponentEntity {

    @Column(nullable = false)
    private Integer wattage;

    @Column(nullable = false, length = 50)
    private String efficiencyRating;

    @Column(nullable = false, length = 50)
    private String formFactor;

    @Column(nullable = false)
    private Boolean isModular;

    @Column(nullable = false)
    private Integer pcieConnectorsCount;

    @Column(nullable = false)
    private Boolean has12VhpwrConnector;

    public PsuEntity(String manufacturer, String model, Double price, Integer tdpWatts,
                     Integer wattage, String efficiencyRating, String formFactor,
                     Boolean isModular, Integer pcieConnectorsCount, Boolean has12VhpwrConnector) {
        super(manufacturer, model, price, tdpWatts);
        this.wattage = wattage;
        this.efficiencyRating = efficiencyRating;
        this.formFactor = formFactor;
        this.isModular = isModular;
        this.pcieConnectorsCount = pcieConnectorsCount;
        this.has12VhpwrConnector = has12VhpwrConnector;
    }
}
