package com.wizardlybump17.wmarket.api.market;

import com.wizardlybump17.wmarket.api.filter.Filter;
import com.wizardlybump17.wlib.inventory.item.ItemButton;
import com.wizardlybump17.wlib.inventory.paginated.PaginatedInventory;
import com.wizardlybump17.wlib.inventory.paginated.PaginatedInventoryBuilder;
import com.wizardlybump17.wlib.item.Item;
import com.wizardlybump17.wlib.item.WMaterial;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ToString
@EqualsAndHashCode
public class MarketCategory {

    private final Category category;
    private final List<ItemStack> items;
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private Market market;

    public void setMarket(Market market) {
        if (this.market == null)
            this.market = market;
    }

    private PaginatedInventory inventory;

    public List<ItemStack> getItems() {
        return new ArrayList<>(items);
    }

    public boolean addItem(ItemStack item) {
        for (Filter filter : category.getFilters())
            if (!filter.accept(item))
                return false;
        items.add(item);
        updateInventory();
        return true;
    }

    public void removeItem(ItemStack item) {
        items.remove(item);
        updateInventory();
    }

    public boolean hasItem(ItemStack item) {
        return items.contains(item);
    }

    private void updateInventory() {
        inventory = new PaginatedInventoryBuilder()
                .title(category.getName())
                .shape("#########" +
                        "#xxxxxxx#" +
                        "#xxxxxxx#" +
                        "#xxxxxxx#" +
                        "#xxxxxxx#" +
                        "<###@###>")
                .shapeReplacement(
                        '#',
                        new ItemButton(Item.builder()
                                .type(WMaterial.STAINED_GLASS_PANE)
                                .durability((short) 15)
                                .displayName(" ")
                                .build()))
                .shapeReplacement(
                        '@',
                        new ItemButton(Item.builder()
                                .type(Material.BARRIER)
                                .displayName("§aBack")
                                .build(),
                                event -> market.getInventory().show(event.getWhoClicked(), 0)))
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
