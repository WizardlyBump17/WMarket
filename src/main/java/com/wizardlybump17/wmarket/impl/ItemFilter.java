package com.wizardlybump17.wmarket.impl;

import com.wizardlybump17.wmarket.api.filter.Filter;
import com.wizardlybump17.wlib.item.Item;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ItemFilter extends Filter {

    private static final Pattern ENDS_WITH = Pattern.compile("\\*(.+)");
    private static final Pattern STARTS_WITH = Pattern.compile("(.+)\\*");
    private static final Pattern CONTAINS = Pattern.compile("\\*(.+)\\*");

    public ItemFilter(String name, Object checker) {
        super(name, checker);
    }

    @Override
    public boolean accept(ItemStack item) {
        if (item == null)
            throw new NullPointerException("item can not be null");

        Item.ItemBuilder builder = Item.fromItemStack(item);

        if (checker == null)
            return true;

        switch (name.toLowerCase()) {
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
                if (builder.getDisplayName() == null)
                    return false;
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
//finish
            case "enchantments": {
                Map<Enchantment, Integer> enchantments = builder.getEnchantments();
                if (checker instanceof List) {
                    List<String> enchantmentNames = ((List<Object>) checker).stream().map(Object::toString).collect(Collectors.toList());
                    boolean find = false;
                    enchantmentsFor: for (String enchantmentName : enchantmentNames) {
                        for (Enchantment enchantment : enchantments.keySet())
                            if (checkString(enchantmentName, enchantment.getName())) {
                                find = true;
                                break enchantmentsFor;
                            }
                    }
                    if (!find)
                        return false;
                }

                if (checker instanceof Map) {
                    Map<Object, Object> map = (Map<Object, Object>) checker;
                    if (map.isEmpty())
                        break;

                    Object firstValue = map.values().iterator().next();
                    if (!(firstValue instanceof Integer) && !(firstValue instanceof String))
                        throw new ClassCastException("enchantments filter with the checker of type Map requires that the values of the map be int or string");

                    boolean find = false;
                    for (Map.Entry<Object, Object> entry : map.entrySet()) {
                        if (find)
                            find = false;
                        String enchantmentName = entry.getKey().toString();
                        int level = Integer.parseInt(entry.getValue().toString());


                    }
                    break;
                }
                break;
            }

            default:
                throw new IllegalArgumentException("unknown filter type \"" + name.toLowerCase() + "\"");
        }
        return true;
    }

    static boolean checkString(String checker, String string) {
        return startsWith(checker, string) || endsWith(checker, string) || contains(checker, string) || checker.replace("\\*", "*").equalsIgnoreCase(string);
    }

    static boolean startsWith(String checker, String string) {
        Matcher matcher = STARTS_WITH.matcher(checker.replace("\\*", "\u0000"));
        if (matcher.matches())
            return string.toLowerCase().startsWith(matcher.group(1).replace("\u0000", "*").toLowerCase());
        return false;
    }

    static boolean endsWith(String checker, String string) {
        Matcher matcher = ENDS_WITH.matcher(checker.replace("\\*", "\u0000"));
        if (matcher.matches()) {
            return string.toLowerCase().endsWith(matcher.group(1).replace("\u0000", "*").toLowerCase());
        }
        return false;
    }

    static boolean contains(String checker, String string) {
        Matcher matcher = CONTAINS.matcher(checker.replace("\\*", "\u0000"));
        if (matcher.matches())
            return string.toLowerCase().contains(matcher.group(1).replace("\u0000", "*").toLowerCase());
        return false;
    }
}
