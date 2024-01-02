package com.project.springflow.Enum;

import lombok.Getter;

@Getter
public enum WorkOrderEnum {
    UNASSIGNED("UNASSIGNED"),
    ASSIGNED("ASSIGNED"),
    ACCEPTED("ACCEPTED"),
    REJECTED("REJECTED"),
    CARRIERNOTFOUND("CARRIERNOTFOUND");

    private final String workOrderStatus;

    WorkOrderEnum(String workOrderStatus){
        this.workOrderStatus = workOrderStatus;
    }
}
