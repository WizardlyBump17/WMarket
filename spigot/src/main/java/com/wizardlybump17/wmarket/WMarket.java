package com.wizardlybump17.wmarket;

import com.wizardlybump17.wlib.command.CommandManager;
import com.wizardlybump17.wlib.command.holder.BukkitCommandHolder;
import com.wizardlybump17.wmarket.command.MarketCommand;
import lombok.Getter;

@Getter
public class WMarket extends com.wizardlybump17.wmarket.api.WMarket {

    @Override
    public void onEnable() {
        super.onEnable();

        new CommandManager(new BukkitCommandHolder(this)).registerCommands(
                new MarketCommand(this)
        );
    }
}
