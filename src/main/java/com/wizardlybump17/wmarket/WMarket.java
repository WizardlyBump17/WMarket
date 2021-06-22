package com.wizardlybump17.wmarket;

import com.wizardlybump17.wlib.item.Item;
import com.wizardlybump17.wmarket.api.filter.Filter;
import com.wizardlybump17.wmarket.api.market.Category;
import com.wizardlybump17.wmarket.api.market.Market;
import com.wizardlybump17.wmarket.api.market.MarketCategory;
import com.wizardlybump17.wmarket.command.MarketCommand;
import com.wizardlybump17.wmarket.impl.ItemFilter;
import com.wizardlybump17.wlib.command.CommandManager;
import com.wizardlybump17.wlib.config.Config;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

@Getter
public class WMarket extends JavaPlugin {

    private Market market;

    private final Config marketConfig = Config.load("market.yml", this);

    @Override
    public void onEnable() {
        loadMarket();

        CommandManager manager = new CommandManager(this);
        manager.registerCommands(new MarketCommand(this));
    }

    private void loadMarket() {
        marketConfig.saveDefaultConfig();

        Set<MarketCategory> categories = new HashSet<>();
        for (Object object : marketConfig.getList("market.categories")) {
            Map<String, Object> data = (Map<String, Object>) object;

            List<Filter> filters = new ArrayList<>();
            for (Object filterObject : (List<Object>) data.getOrDefault("filters", new ArrayList<>())) {
                Map<String, Object> filterData = (Map<String, Object>) filterObject;
                filterData.forEach((type, checker) -> filters.add(new ItemFilter(type, checker)));
            }

            categories.add(new MarketCategory(new Category(
                    data.get("name").toString(),
                    filters, 
                    data.containsKey("icon") ? Item.deserialize((Map<String, Object>) data.get("icon")).build() : null),
                    new ArrayList<>()));
        }

        market = new Market(categories);
        categories.forEach(category -> category.setMarket(market));
        market.updateInventory();
    }

    public static WMarket getInstance() {
        return getPlugin(WMarket.class);
    }
}
