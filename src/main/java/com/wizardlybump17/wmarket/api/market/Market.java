package com.wizardlybump17.wmarket.api.market;

import com.wizardlybump17.wlib.inventory.item.ItemButton;
import com.wizardlybump17.wlib.inventory.paginated.PaginatedInventory;
import com.wizardlybump17.wlib.inventory.paginated.PaginatedInventoryBuilder;
import com.wizardlybump17.wlib.item.Item;
import com.wizardlybump17.wlib.item.WMaterial;
import lombok.Data;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Set;
import java.util.stream.Collectors;

@Data
public class Market {

    private final Set<MarketCategory> categories;

    private PaginatedInventory inventory;

    public MarketCategory publish(ItemStack item) {
        for (MarketCategory marketCategory : categories)
            if (marketCategory.addItem(item)) {
                updateInventory();
                return marketCategory;
            }
        return null;
    }

    public void updateInventory() {
        inventory = new PaginatedInventoryBuilder()
                .title("Market")
                .shape("#########" +
                        "#xxxxxxx#" +
                        "#xxxxxxx#" +
                        "#xxxxxxx#" +
                        "#xxxxxxx#" +
                        "<#######>")
                .shapeReplacement(
                        '#',
                        new ItemButton(Item.builder()
                                .type(WMaterial.STAINED_GLASS_PANE)
                                .durability((short) 15)
                                .displayName(" ")
                                .build()))
                .nextPage(new PaginatedInventoryBuilder.InventoryNavigator(
                        Item.builder()
                                .type(Material.ARROW)
                                .displayName("§aNext page")
                                .build(),
                        '#'))
                .previousPage(new PaginatedInventoryBuilder.InventoryNavigator(
                        Item.builder()
                                .type(Material.ARROW)
                                .displayName("§aPrevious page")
                                .build(),
                        '#'))
                .content(categories.stream()
                        .map(category -> new ItemButton(
                                Item.builder()
                                        .type(Material.CHEST)
                                        .displayName("§a" + category.getCategory().getName())
                                        .lore("§7" + category.getItems().size() + " items")
                                        .build(),
                                event -> {
                                    if (category.getInventory() != null)
                                        category.getInventory().show(event.getWhoClicked(), 0);
                                }))
                        .collect(Collectors.toList()))
                .build();
    }
}
