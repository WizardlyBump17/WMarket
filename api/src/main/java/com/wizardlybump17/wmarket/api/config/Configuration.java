package com.wizardlybump17.wmarket.api.config;

import com.wizardlybump17.wlib.config.ConfigInfo;
import com.wizardlybump17.wlib.config.Path;
import com.wizardlybump17.wlib.inventory.item.ItemButton;
import com.wizardlybump17.wlib.inventory.paginated.PaginatedInventoryBuilder;
import com.wizardlybump17.wlib.item.ItemBuilder;
import com.wizardlybump17.wlib.util.bukkit.NumberFormatter;
import com.wizardlybump17.wmarket.api.WMarket;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

@ConfigInfo(name = "configs/global.yml", holderType = WMarket.class)
@UtilityClass
public class Configuration {

    @Path("number-formatter")
    public static NumberFormatter numberFormatter = new NumberFormatter(com.wizardlybump17.wlib.util.NumberFormatter.SIMPLE_FORMATTER.getSuffixes());

    @UtilityClass
    @ConfigInfo(name = "configs/market.yml", holderType = WMarket.class)
    public static class Market {

        @Path("categories-inventory")
        public static PaginatedInventoryBuilder categoriesInventory = PaginatedInventoryBuilder.create()
                .title("Market")
                .shape("         " +
                        "  A S P  " +
                        "         ")
                .shapeReplacement('A', new ItemButton(
                        new ItemBuilder()
                                .type(Material.IRON_AXE)
                                .displayName("§aAxes")
                                .lore(
                                        "§7Click to see the axes",
                                        "§7Available axes: §f{items}"
                                )
                                .itemFlags(ItemFlag.HIDE_ATTRIBUTES)
                                .customData("category", "axes")
                ))
                .shapeReplacement('S', new ItemButton(
                        new ItemBuilder()
                                .type(Material.IRON_SWORD)
                                .displayName("§aSwords")
                                .lore(
                                        "§7Click to see the swords",
                                        "§7Available axes: §f{items}"
                                )
                                .itemFlags(ItemFlag.HIDE_ATTRIBUTES)
                                .customData("category", "swords")
                ))
                .shapeReplacement('P', new ItemButton(
                        new ItemBuilder()
                                .type(Material.IRON_PICKAXE)
                                .displayName("§aPickaxes")
                                .lore(
                                        "§7Click to see the pickaxes",
                                        "§7Available axes: §f{items}"
                                )
                                .itemFlags(ItemFlag.HIDE_ATTRIBUTES)
                                .customData("category", "pickaxes")
                ));
    }
}
