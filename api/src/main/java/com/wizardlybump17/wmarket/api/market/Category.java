package com.wizardlybump17.wmarket.api.market;

import com.wizardlybump17.wlib.item.ItemBuilder;
import com.wizardlybump17.wlib.item.ItemFilter;
import com.wizardlybump17.wlib.util.MapUtils;
import lombok.Data;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Data
@SerializableAs("wmarket-category")
public class Category implements ConfigurationSerializable {

    private final String name;
    private final ItemFilter itemFilter;
    private final ItemBuilder icon;

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        return MapUtils.mapOf(
                "name", name,
                "filter", itemFilter,
                "icon", icon
        );
    }

    public static Category deserialize(Map<String, Object> map) {
        return new Category(
                (String) map.get("name"),
                (ItemFilter) map.get("filter"),
                (ItemBuilder) map.get("icon")
        );
    }
}
