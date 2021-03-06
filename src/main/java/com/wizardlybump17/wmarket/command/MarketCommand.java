package com.wizardlybump17.wmarket.command;

import com.wizardlybump17.wmarket.WMarket;
import com.wizardlybump17.wmarket.api.market.MarketCategory;
import com.wizardlybump17.wlib.command.Command;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class MarketCommand {

    private final WMarket plugin;

    @Command(execution = "market")
    public void market(Player player) {
        player.sendMessage(
                "" +
                        "\n   §a/market sell <price>" +
                        "\n   §a/market sell <price> [player]" +
                        "\n   §a/market see" +
                        "\n "
        );
    }

    @Command(execution = "market sell")
    public void marketSell(Player player) {
        player.sendMessage(
                "" +
                        "\n   §a/market sell <price>" +
                        "\n   §a/market sell <price> [player]" +
                        "\n "
        );
    }

    @Command(execution = "market sell <price> [player]")
    public void marketSell(Player player, String priceString, String targetName) {
        ItemStack item = player.getItemInHand();
        if (item.getType() == Material.AIR) {
            player.sendMessage("§cYou need to hold the item to sell it!");
            return;
        }

        MarketCategory category = plugin.getMarket().publish(item);
        if (category == null) {
            player.sendMessage("§cWe didn't found a category for your item...");
            return;
        }

        player.sendMessage("§aYour item has been placed in the " + category.getCategory().getName() + " category with the price " + priceString + "!");
        player.setItemInHand(null);
    }

    @Command(execution = "market see")
    public void marketSee(Player player) {
        plugin.getMarket().getInventory().show(player, 0);
    }
}
