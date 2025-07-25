package com.fiap.foodcore.domain;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class Menu {
    private final Long id;
    private final String name;
    private final String description;
    private final List<Item> items;
    private final Restaurant restaurantId;

    private Menu(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.items = builder.itemsList == null
                ? Collections.emptyList()
                : Collections.unmodifiableList(new ArrayList<>(builder.itemsList));
        this.restaurantId = builder.restaurantId;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Item> getItems() {
        return items;
    }

    public Restaurant getRestaurantId() {
        return restaurantId;
    }


    public Menu atualizarInformacoes(String novoNome, String novaDescricao, List<Item> novaListaItens) {

        String nomeAtualizado = novoNome != null ? novoNome : this.name;
        String descricaoAtualizada = novaDescricao != null ? novaDescricao : this.description;

        List<Item> itensAtualizados = new ArrayList<>();
        for (Item novoItem : novaListaItens) {
            Item existente = this.items.stream()
                    .filter(i -> i.getId().equals(novoItem.getId()))
                    .findFirst()
                    .orElse(null);
            if (existente != null) {

                Item itemAtualizado = new Item.Builder()
                        .id(existente.getId())
                        .name(novoItem.getName())
                        .description(novoItem.getDescription())
                        .price(novoItem.getPrice())
                        .availability(novoItem.getAvailability())
                        .photo(novoItem.getPhoto())
                        .build();
                itensAtualizados.add(itemAtualizado);
            } else {
                itensAtualizados.add(novoItem);
            }
        }

        return new Menu.Builder()
                .id(this.id)
                .name(nomeAtualizado)
                .description(descricaoAtualizada)
                .itemsList(itensAtualizados)
                .build();
    }

    public static class Builder {
        private Long id;
        private String name;
        private String description;
        private List<Item> itemsList;
        private Restaurant restaurantId;

        public Builder() {
        }

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

        public Builder itemsList(List<Item> itemsList) {
            this.itemsList = itemsList;
            return this;
        }

        public Menu build() {
            Objects.requireNonNull(name, "Menu name is required");
            return new Menu(this);
        }

        public Builder restaurantId(Restaurant restaurantId) {
            Objects.requireNonNull(name, "Restaurant id is required");
            this.restaurantId = restaurantId;
            return this;
        }
    }
}
