package com.wizardlybump17.wmarket.api.config;

import com.wizardlybump17.wlib.config.ConfigInfo;
import com.wizardlybump17.wlib.config.Path;
import com.wizardlybump17.wlib.util.bukkit.NumberFormatter;
import com.wizardlybump17.wmarket.api.WMarket;
import lombok.experimental.UtilityClass;

@ConfigInfo(name = "configs/global.yml", holderType = WMarket.class)
@UtilityClass
public class Configuration {

    @Path("number-formatter")
    public static NumberFormatter numberFormatter = new NumberFormatter(com.wizardlybump17.wlib.util.NumberFormatter.SIMPLE_FORMATTER.getSuffixes());
}
