package com.wizardlybump17.market.api.filter;

import lombok.Data;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Data
public abstract class Filter {

    private final Map<String, FilterType> filterTypes;

    public void addFilterType(FilterType type) {
        filterTypes.put(type.getName(), type);
    }

    public FilterType getFilterType(String name) {
        return filterTypes.get(name.toLowerCase());
    }

    public void removerFilterType(String name) {
        filterTypes.remove(name.toLowerCase());
    }

    public abstract boolean accept(ItemStack item);
}
