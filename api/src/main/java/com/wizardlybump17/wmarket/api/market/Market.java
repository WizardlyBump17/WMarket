package com.wizardlybump17.wmarket.api.market;

import com.wizardlybump17.wlib.inventory.item.ItemButton;
import com.wizardlybump17.wlib.inventory.paginated.PaginatedInventory;
import com.wizardlybump17.wlib.inventory.paginated.PaginatedInventoryBuilder;
import com.wizardlybump17.wlib.item.ItemBuilder;
import com.wizardlybump17.wmarket.api.config.Configuration;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Data
public class Market {

    private final Map<String, MarketCategory> categories;

    public MarketCategory publish(ItemStack item) {
        for (MarketCategory marketCategory : categories.values())
            if (marketCategory.addItem(item)) {
                return marketCategory;
            }
        return null;
    }

    public PaginatedInventory getInventory() {
        PaginatedInventoryBuilder builder = Configuration.Market.categoriesInventory.clone();

        for (Map.Entry<Character, ItemButton> entry : builder.shapeReplacements().entrySet()) {
            ItemButton button = entry.getValue();
            entry.setValue(getCategoryItem(button));
        }

        return builder.build();
    }

    private ItemButton getCategoryItem(ItemButton button) {
        String categoryName = (String) button.getCustomData().get("category");
        if (categoryName == null)
            return button;

        MarketCategory category = categories.get(categoryName.toLowerCase());
        if (category == null)
            return button;

        return new ItemButton(
                ItemBuilder.fromItemStack(button.getItem().get())
                        .replaceDisplayNameLore(Map.of(
                                "{category}", category.getCategory().getName(),
                                "{items}", String.valueOf(category.getItems().size())
                        )),
                (event, inventory) -> category.getInventory().show(event.getWhoClicked()),
                button.getCustomData()
        );
    }
}
