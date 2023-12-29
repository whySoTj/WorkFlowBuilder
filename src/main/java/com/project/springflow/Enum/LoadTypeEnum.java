package com.project.springflow.Enum;

import lombok.Getter;

@Getter
public enum LoadTypeEnum {
    LCL("LCL"),
    FCL("FCL");

    private final String loadTypeEnum;

    LoadTypeEnum (String loadTypeEnum){
        this.loadTypeEnum =loadTypeEnum;
    }

}
