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
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public abstract class WMarket extends JavaPlugin {

    private Market market;

    @Override
    public void onLoad() {
        ConfigurationSerialization.registerClass(Category.class);
    }

    @Override
    public void onEnable() {
        initConfigurations();
        loadMarket();
    }

    private void loadMarket() {
        Map<String, MarketCategory> categories = new LinkedHashMap<>();
        market = new Market(categories);

        for (Object object : Config.load("categories.yml", this).getList("categories", Collections.emptyList())) {
            Category category = (Category) object;
            MarketCategory marketCategory = new MarketCategory(category, new ArrayList<>());
            marketCategory.setMarket(market);
            categories.put(category.getName().toLowerCase(), marketCategory);
        }

        for (MarketCategory category : categories.values())
            category.setMarket(market);
    }

    protected abstract void initConfigurations();

    public static WMarket getInstance() {
        return getPlugin(WMarket.class);
    }
}
