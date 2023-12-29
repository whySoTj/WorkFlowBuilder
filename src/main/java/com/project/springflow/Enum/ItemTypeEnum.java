package com.project.springflow.Enum;

import lombok.Getter;

@Getter
public enum ItemTypeEnum {
    NORMAL("NORMAL"),
    FRAGILE("FRAGILE"),
    HAZMAT("HAZMAT"),
    PERISHIBLE("PERISHIBLE");

    private final String itemTypeEnum;

    ItemTypeEnum (String itemTypeEnum){
        this.itemTypeEnum = itemTypeEnum;
    }
}