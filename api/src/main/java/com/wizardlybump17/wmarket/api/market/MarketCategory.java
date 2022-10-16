package com.wizardlybump17.wmarket.api.market;

import com.wizardlybump17.wlib.inventory.item.InventoryNavigator;
import com.wizardlybump17.wlib.inventory.item.ItemButton;
import com.wizardlybump17.wlib.inventory.paginated.PaginatedInventory;
import com.wizardlybump17.wlib.inventory.paginated.PaginatedInventoryBuilder;
import com.wizardlybump17.wlib.item.ItemBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@EqualsAndHashCode
public class MarketCategory {

    private final Category category;
    private final List<ItemStack> items;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Market market;

    public void setMarket(Market market) {
        if (this.market == null)
            this.market = market;
    }

    public List<ItemStack> getItems() {
        return new ArrayList<>(items);
    }

    public boolean addItem(ItemStack item) {
        if (!category.getItemFilter().accept(item))
            return false;

        items.add(item);
        return true;
    }

    public void removeItem(ItemStack item) {
        items.remove(item);
    }

    public boolean hasItem(ItemStack item) {
        return items.contains(item);
    }

    public PaginatedInventory getInventory() {
        return PaginatedInventoryBuilder.create()
                .title(category.getName())
                .shape("#########" +
                        "#xxxxxxx#" +
                        "#xxxxxxx#" +
                        "#xxxxxxx#" +
                        "#xxxxxxx#" +
                        "<###@###>")
                .shapeReplacement(
                        '#',
                        new ItemButton(new ItemBuilder()
                                .type(Material.BLACK_STAINED_GLASS_PANE)
                                .displayName(" ")
                                .build()))
                .shapeReplacement(
                        '@',
                        new ItemButton(new ItemBuilder()
                                .type(Material.BARRIER)
                                .displayName("§aBack")
                                .build(),
                                (event, inventory) -> market.getInventory().show(event.getWhoClicked(), 0)))
                .nextPage(InventoryNavigator.NEXT_PAGE)
                .previousPage(InventoryNavigator.PREVIOUS_PAGE)
                .content(getItems().stream()
                        .map(item -> new ItemButton(
                                item,
                                (event, inventory) -> event.getWhoClicked().sendMessage(item.toString())))
                        .toList())
                .build();
    }
}
