package com.wizardlybump17.wmarket.api.market;

import com.wizardlybump17.wlib.inventory.item.InventoryNavigator;
import com.wizardlybump17.wlib.inventory.item.ItemButton;
import com.wizardlybump17.wlib.inventory.paginated.PaginatedInventory;
import com.wizardlybump17.wlib.inventory.paginated.PaginatedInventoryBuilder;
import com.wizardlybump17.wlib.item.ItemBuilder;
import lombok.Data;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Set;

@Data
public class Market {

    private final Set<MarketCategory> categories;

    public MarketCategory publish(ItemStack item) {
        for (MarketCategory marketCategory : categories)
            if (marketCategory.addItem(item)) {
                return marketCategory;
            }
        return null;
    }

    public PaginatedInventory getInventory() {
        return PaginatedInventoryBuilder.create()
                .title("Market")
                .shape("#########" +
                        "#xxxxxxx#" +
                        "#xxxxxxx#" +
                        "#xxxxxxx#" +
                        "#xxxxxxx#" +
                        "<#######>")
                .shapeReplacement(
                        '#',
                        new ItemButton(new ItemBuilder()
                                .type(Material.BLACK_STAINED_GLASS_PANE)
                                .displayName(" ")
                                .build()))
                .nextPage(InventoryNavigator.NEXT_PAGE)
                .previousPage(InventoryNavigator.PREVIOUS_PAGE)
                .content(categories.stream()
                        .map(category -> new ItemButton(
                                category.getCategory().getIcon().clone()
                                        .replaceDisplayNameLore(Map.of(
                                                "{items}", String.valueOf(category.getItems().size())
                                        ))
                                        .build(),
                                (event, inventory) -> category.getInventory().show(event.getWhoClicked())
                        ))
                        .toList())
                .build();
    }
}
