package com.wizardlybump17.wmarket.command;

import com.wizardlybump17.wlib.command.Command;
import com.wizardlybump17.wlib.command.sender.PlayerSender;
import com.wizardlybump17.wmarket.WMarket;
import com.wizardlybump17.wmarket.api.market.MarketCategory;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public record MarketCommand(WMarket plugin) {

    @Command(execution = "market")
    public void market(PlayerSender player) {
        player.sendMessage(
                "" +
                        "\n   §a/market sell <price>" +
                        "\n   §a/market sell <price> [player]" +
                        "\n   §a/market see" +
                        "\n "
        );
    }

    @Command(execution = "market sell")
    public void marketSell(PlayerSender player) {
        player.sendMessage(
                "" +
                        "\n   §a/market sell <price>" +
                        "\n   §a/market sell <price> [player]" +
                        "\n "
        );
    }

    @Command(execution = "market sell <price>")
    public void marketSell(PlayerSender player, double price) {
        ItemStack item = player.getHandle().getInventory().getItemInMainHand();
        if (item.getType() == Material.AIR) {
            player.sendMessage("§cYou need to hold the item to sell it!");
            return;
        }

        MarketCategory category = plugin.getMarket().publish(item);
        if (category == null) {
            player.sendMessage("§cWe didn't found a category for your item...");
            return;
        }

        player.sendMessage("§aYour item has been placed in the " + category.getCategory().getName() + " category with the price " + price + "!");
        player.getHandle().getInventory().setItemInMainHand(null);
    }

    @Command(execution = "market see")
    public void marketSee(PlayerSender player) {
        plugin.getMarket().getInventory().show(player.getHandle());
    }
}
