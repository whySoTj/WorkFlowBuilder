package com.project.springflow.Enum;

import lombok.Getter;

@Getter
public enum CarrierEnum {
    AVAILABLE("AVAILABLE"),
    ELIGIBLE("ELIGIBLE"),
    NOTAVAILABLE("NOT AVAILABLE");

    private final String carrierStatus;

    CarrierEnum(String carrierStatus){
        this.carrierStatus = carrierStatus;
    }
}
