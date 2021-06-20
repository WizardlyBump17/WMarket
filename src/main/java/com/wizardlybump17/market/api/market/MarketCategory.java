package com.wizardlybump17.market.api.market;

import com.wizardlybump17.wlib.inventory.item.ItemButton;
import com.wizardlybump17.wlib.inventory.paginated.PaginatedInventory;
import com.wizardlybump17.wlib.inventory.paginated.PaginatedInventoryBuilder;
import com.wizardlybump17.wlib.item.Item;
import com.wizardlybump17.wlib.item.WMaterial;
import lombok.Data;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MarketCategory {

    private final Category category;
    private final List<ItemStack> items;

    private PaginatedInventory inventory;

    public List<ItemStack> getItems() {
        return new ArrayList<>(items);
    }

    public void addItem(ItemStack item) {
        items.add(item);
        updateInventory();
    }

    public void removeItem(ItemStack item) {
        items.remove(item);
        updateInventory();
    }

    public boolean hasItem(ItemStack item) {
        return items.contains(item);
    }

    void updateInventory() {
        inventory = new PaginatedInventoryBuilder()
                .title(category.getName())
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
                .content(getItems().stream()
                        .map(item -> new ItemButton(
                                item,
                                event -> event.getWhoClicked().sendMessage(item.toString())))
                        .collect(Collectors.toList()))
                .build();
    }
}
