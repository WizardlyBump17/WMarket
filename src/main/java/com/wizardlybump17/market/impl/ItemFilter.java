package com.wizardlybump17.market.impl;

import com.wizardlybump17.market.api.filter.Filter;
import com.wizardlybump17.market.api.filter.FilterType;
import com.wizardlybump17.wlib.item.Item;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ItemFilter extends Filter {

    private static final Pattern ENDS_WITH = Pattern.compile("\\*(.+)");
    private static final Pattern STARTS_WITH = Pattern.compile("(.+)\\*");
    private static final Pattern CONTAINS = Pattern.compile("\\*(.+)\\*");

    public ItemFilter(Map<String, FilterType> filterTypes) {
        super(filterTypes);
    }

    @Override
    public boolean accept(ItemStack item) {
        if (item == null)
            throw new NullPointerException("item can not be null");

        Item.ItemBuilder builder = Item.fromItemStack(item);
        Map<String, FilterType> filters = getFilterTypes();

        for (FilterType filter : filters.values()) {
            Object checker = filter.getChecker();
            switch (filter.getName().toLowerCase()) {
                case "type": {
                    if (checker instanceof String) {
                        if (!checkString(checker.toString(), builder.getType().name()))
                            return false;
                        break;
                    }

                    if (!(checker instanceof Material))
                        throw new ClassCastException("valid types for the \"type\" filter are String and Material");
                    if (!checkString(((Material) checker).name(), builder.getType().name()))
                        return false;
                    break;
                }

                case "display-name": {
                    if (!(checker instanceof String))
                        throw new ClassCastException("display-name filter requires string");
                    if (!checkString(checker.toString(), builder.getDisplayName()))
                        return false;
                    break;
                }

                case "lore": {
                    if (!(checker instanceof List))
                        throw new ClassCastException("lore filter requires List");

                    List<String> lore = ((List<Object>) checker).stream().map(Object::toString).collect(Collectors.toList());
                    boolean find = false;
                    loreFor: for (String string : lore) {
                        for (String itemLore : builder.getLore())
                            if (checkString(string, itemLore)) {
                                find = true;
                                break loreFor;
                            }
                    }
                    if (!find)
                        return false;
                    break;
                }

                default:
                    throw new IllegalArgumentException("unknown filter type \"" + filter.getName().toLowerCase() + "\"");
            }
        }

        return true;
    }

    static boolean checkString(String checker, String string) {
        return startsWith(checker, string) || endsWith(checker, string) || contains(checker, string) || checker.equals(string);
    }

    static boolean startsWith(String checker, String string) {
        Matcher matcher = STARTS_WITH.matcher(checker);
        if (matcher.matches())
            return string.startsWith(matcher.group(1));
        return false;
    }

    static boolean endsWith(String checker, String string) {
        Matcher matcher = ENDS_WITH.matcher(checker);
        if (matcher.matches())
            return string.startsWith(matcher.group(1));
        return false;
    }

    static boolean contains(String checker, String string) {
        Matcher matcher = CONTAINS.matcher(checker);
        if (matcher.matches())
            return string.startsWith(matcher.group(1));
        return false;
    }
}
