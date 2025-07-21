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

    private Item(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.price = builder.price;
        this.availability = builder.availability;
        this.photo = builder.photo;
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public String getAvailability() { return availability; }
    public String getPhoto() { return photo; }

    public Item atualizarInformacoes(String novoNome, String novaDescricao, BigDecimal novoPreco, String novaDisponibilidade, String novaFoto) {
        return new Builder()
                .id(this.id)
                .name(novoNome != null ? novoNome : this.name)
                .description(novaDescricao != null ? novaDescricao : this.description)
                .price(novoPreco != null ? novoPreco : this.price)
                .availability(novaDisponibilidade != null ? novaDisponibilidade : this.availability)
                .photo(novaFoto != null ? novaFoto : this.photo)
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
        public Item build() {
            Objects.requireNonNull(name, "Item name is required");
            Objects.requireNonNull(price, "Item price is required");
            return new Item(this);
        }
    }
}
