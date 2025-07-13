package com.fiap.foodcore.infrastructure.gateways.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "restaurante")
@Getter
@Setter
@NoArgsConstructor
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RestaurantAddressEntity> address;
    private String cuisineType;
    private String openingHours;
    private String closingHours;
    private Long ownerId;

}
