package com.wizardlybump17.wmarket;

import com.wizardlybump17.wlib.command.CommandManager;
import com.wizardlybump17.wlib.command.holder.BukkitCommandHolder;
import com.wizardlybump17.wlib.config.holder.BukkitConfigHolderFactory;
import com.wizardlybump17.wlib.config.registry.ConfigHandlerRegistry;
import com.wizardlybump17.wlib.config.registry.ConfigHolderFactoryRegistry;
import com.wizardlybump17.wmarket.api.config.Configuration;
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

    @Override
    protected void initConfigurations() {
        BukkitConfigHolderFactory factory = new BukkitConfigHolderFactory(this);
        ConfigHolderFactoryRegistry.getInstance().put(WMarket.class, factory);
        ConfigHolderFactoryRegistry.getInstance().put(com.wizardlybump17.wmarket.api.WMarket.class, factory);

        ConfigHandlerRegistry.getInstance().register(Configuration.class);

        ConfigHandlerRegistry.getInstance().register(MarketCommand.class);
    }
}
