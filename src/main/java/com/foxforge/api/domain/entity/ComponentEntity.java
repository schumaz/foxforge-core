package com.foxforge.api.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tb_component")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ComponentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String manufacturer;

    @Column(nullable = false, length = 150)
    private String model;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer tdpWatts;

    public ComponentEntity(String manufacturer, String model, Double price, Integer tdpWatts) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.price = price;
        this.tdpWatts = tdpWatts;
    }
}
