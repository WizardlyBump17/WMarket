package com.wizardlybump17.market.api.filter;

import lombok.Data;

@Data
public class FilterType {

    private final String name;
    private final Object checker;
}
