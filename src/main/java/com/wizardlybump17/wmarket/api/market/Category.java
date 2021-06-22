package com.wizardlybump17.wmarket.api.market;

import com.wizardlybump17.wmarket.api.filter.Filter;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Data
public class Category {

    private final String name;
    private final List<Filter> filters;
    private final ItemStack icon;

    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    public void removeFilter(Filter filter) {
        filters.remove(filter);
    }

    public boolean hasFilter(Filter filter) {
        return filters.contains(filter);
    }
}
