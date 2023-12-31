package com.project.springflow.Enum;

import lombok.Getter;

@Getter
public enum ConfigEnum {
        AUTOMATIC("SELECT CARRIER AUTOMATICALLY"),
        FASTDELIVERY("FAST DELIVERY"),
        MANUAL("SELECT CARRIER MANAUALLY");

        private final String configuration;

        ConfigEnum (String configuration){
            this.configuration = configuration;
        }

}
