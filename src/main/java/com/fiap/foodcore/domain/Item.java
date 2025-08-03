package com.fiap.foodcore.domain;


import java.math.BigDecimal;
import java.util.Objects;

public class Item {
    private final Long id;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final String availability;
    private final String photo;
    private final Long ownerId;

    private Item(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.price = builder.price;
        this.availability = builder.availability;
        this.photo = builder.photo;
        this.ownerId = builder.ownerId;
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public String getAvailability() { return availability; }
    public String getPhoto() { return photo; }
    public Long getOwnerId() { return ownerId; }

    public Item atualizarInformacoes(String novoNome, String novaDescricao, BigDecimal novoPreco, String novaDisponibilidade, String novaFoto) {
        return new Builder()
                .id(this.id)
                .name(novoNome != null ? novoNome : this.name)
                .description(novaDescricao != null ? novaDescricao : this.description)
                .price(novoPreco != null ? novoPreco : this.price)
                .availability(novaDisponibilidade != null ? novaDisponibilidade : this.availability)
                .photo(novaFoto != null ? novaFoto : this.photo)
                .ownerId(this.ownerId) //
                .build();
    }

    // Builder
    public static class Builder {
        private Long id;
        private String name;
        private String description;
        private BigDecimal price;
        private String availability;
        private String photo;
        private Long ownerId;

        public Builder() {}

        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public Builder description(String description) {
            this.description = description;
            return this;
        }
        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }
        public Builder availability(String availability) {
            this.availability = availability;
            return this;
        }
        public Builder photo(String photo) {
            this.photo = photo;
            return this;
        }
        public Builder ownerId(Long ownerId) {
            this.ownerId = ownerId;
            return this;
        }
        public Item build() {
            Objects.requireNonNull(name, "O nome do item é obrigatório.");
            Objects.requireNonNull(price, "O preço do item é obrigatório.");
            return new Item(this);
        }
    }
}
