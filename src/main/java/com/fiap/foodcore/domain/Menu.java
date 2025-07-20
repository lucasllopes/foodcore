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
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public List<Item> getItems() { return items; }

    // Builder
    public static class Builder {
        private Long id;
        private String name;
        private String description;
        private List<Item> itemsList;

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
