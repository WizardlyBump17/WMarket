package com.wizardlybump17.wmarket.api.filter;

import lombok.Data;
import org.bukkit.inventory.ItemStack;

@Data
public abstract class Filter {

    protected final String name;
    protected final Object checker;

    public abstract boolean accept(ItemStack item);
}
