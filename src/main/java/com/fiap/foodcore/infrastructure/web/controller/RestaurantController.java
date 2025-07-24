package com.fiap.foodcore.infrastructure.web.controller;

import com.fiap.foodcore.infrastructure.web.controller.dto.restaurant.RestaurantCreateRequestDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.restaurant.RestaurantResponseDTO;
import com.fiap.foodcore.infrastructure.web.controller.dto.restaurant.RestaurantUpdateRequestDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(name = "Restaurante", description = "Endpoint para CRUD de restaurante")
public interface RestaurantController {

    ResponseEntity<RestaurantResponseDTO> findRestaurantById(Long id);

    ResponseEntity<Page<RestaurantResponseDTO>> listPaginatedRestaurants(String name, Pageable pageable);

    ResponseEntity<RestaurantResponseDTO> createRestaurant(RestaurantCreateRequestDTO dto);

    ResponseEntity<RestaurantResponseDTO> updateRestaurant(Long id, RestaurantUpdateRequestDTO dto);

    ResponseEntity<Void> deleteRestaurant(Long id);
}
