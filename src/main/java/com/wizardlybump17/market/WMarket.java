package com.wizardlybump17.market;

import org.bukkit.plugin.java.JavaPlugin;

public class WMarket extends JavaPlugin {

    @Override
    public void onEnable() {
        super.onEnable();
    }

    private void loadMarket() {
        loadCategories();
    }

    private void loadCategories() {
        loadFilters();
    }

    private void loadFilters() {

    }

    public static WMarket getInstance() {
        return getPlugin(WMarket.class);
    }
}
