package com.wizardlybump17.wmarket.command;

import com.wizardlybump17.wlib.command.Command;
import com.wizardlybump17.wlib.command.sender.PlayerSender;
import com.wizardlybump17.wlib.config.ConfigInfo;
import com.wizardlybump17.wlib.config.Path;
import com.wizardlybump17.wmarket.WMarket;
import com.wizardlybump17.wmarket.api.config.Configuration;
import com.wizardlybump17.wmarket.api.market.MarketCategory;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@ConfigInfo(name = "configs/commands/market.yml", holderType = WMarket.class)
public record MarketCommand(WMarket plugin) {

    @Path(value = "messages.help", options = "fancy")
    public static String helpMessage = "\n§a    /market sell <price>\n    /market sell <price> <player>\n    /market see\n ";
    @Path(value = "messages.not-holding-item", options = "fancy")
    public static String notHoldingItemMessage = "§cYou are not holding any item!";
    @Path(value = "messages.not-found-category", options = "fancy")
    public static String notFoundCategoryMessage = "§cCould not find a category for this item!";
    @Path(value = "messages.item-announced", options = "fancy")
    public static String itemAnnouncedMessage = "§aYour item was announced at the {category} category with the price of {price}!";

    @Command(execution = "market")
    public void market(PlayerSender player) {
        player.sendMessage(helpMessage);
    }

    @Command(execution = "market sell <price>")
    public void marketSell(PlayerSender player, double price) {
        ItemStack item = player.getHandle().getInventory().getItemInMainHand();
        if (item.getType() == Material.AIR) {
            player.sendMessage(notHoldingItemMessage);
            return;
        }

        MarketCategory category = plugin.getMarket().publish(item);
        if (category == null) {
            player.sendMessage(notFoundCategoryMessage);
            return;
        }

        player.sendMessage(
                itemAnnouncedMessage
                        .replace("{category}", category.getCategory().getName())
                        .replace("{price}", Configuration.numberFormatter.formatNumber(price))
        );
        player.getHandle().getInventory().setItemInMainHand(null);
    }

    @Command(execution = "market see")
    public void marketSee(PlayerSender player) {
        plugin.getMarket().getInventory().show(player.getHandle());
    }
}
