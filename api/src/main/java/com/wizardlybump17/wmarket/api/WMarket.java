package com.wizardlybump17.wmarket.api;

import com.wizardlybump17.wlib.config.Config;
import com.wizardlybump17.wmarket.api.market.Category;
import com.wizardlybump17.wmarket.api.market.Market;
import com.wizardlybump17.wmarket.api.market.MarketCategory;
import lombok.Getter;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class WMarket extends JavaPlugin {

    private Market market;

    @Override
    public void onLoad() {
        ConfigurationSerialization.registerClass(Category.class);
    }

    @Override
    public void onEnable() {
        loadMarket();
    }

    private void loadMarket() {
        Set<MarketCategory> categories = new LinkedHashSet<>();

        for (Object object : Config.load("categories.yml", this).getList("categories", Collections.emptyList()))
            categories.add(new MarketCategory((Category) object, new ArrayList<>()));

        market = new Market(categories);
        categories.forEach(category -> category.setMarket(market));
    }

    public static WMarket getInstance() {
        return getPlugin(WMarket.class);
    }
}
