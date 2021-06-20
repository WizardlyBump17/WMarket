package com.wizardlybump17.market.api.market;

import com.wizardlybump17.market.api.filter.Filter;
import lombok.Data;

import java.util.List;

@Data
public class Category {

    private final String name;
    private final List<Filter> filters;

    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    public void removeFilter(Filter filter) {
        filters.remove(filter);
    }

    public boolean hasFilter(Filter filter) {
        return filters.contains(filter);
    }
}
