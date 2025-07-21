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

    private Menu(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.items = builder.itemsList == null
                ? Collections.emptyList()
                : Collections.unmodifiableList(new ArrayList<>(builder.itemsList));
    }

    // Getters
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


    public Menu atualizarInformacoes(String novoNome, String novaDescricao, List<Item> novaListaItens) {
        // Atualiza nome e descrição se fornecidos
        String nomeAtualizado = novoNome != null ? novoNome : this.name;
        String descricaoAtualizada = novaDescricao != null ? novaDescricao : this.description;

        // Sincroniza itens: adiciona novos, remove ausentes e atualiza existentes
        List<Item> itensAtualizados = new ArrayList<>();
        for (Item novoItem : novaListaItens) {
            Item existente = this.items.stream()
                    .filter(i -> i.getId().equals(novoItem.getId()))
                    .findFirst()
                    .orElse(null);
            if (existente != null) {
                // Cria um novo Item com os dados atualizados
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
                itensAtualizados.add(novoItem); // Adiciona novo item
            }
        }
        // Remove itens que não estão na nova lista
        // (itensAtualizados já contém apenas os itens desejados)

        return new Menu.Builder()
                .id(this.id)
                .name(nomeAtualizado)
                .description(descricaoAtualizada)
                .itemsList(itensAtualizados)
                .build();
    }


    // Builder
    public static class Builder {
        private Long id;
        private String name;
        private String description;
        private List<Item> itemsList;

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
    }
}
