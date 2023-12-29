package com.project.springflow.Enum;

import lombok.Getter;

@Getter
public enum WorkOrderAssignableCarrierEnum {
    UNASSIGNED("UNASSIGNED"),
    ASSIGNED("ASSIGNED"),
    COMPLETED("COMPLETED"),
    REJECTED("REJECTED");

    private final String workOrderAssignableCarrierEnum;

    WorkOrderAssignableCarrierEnum (String workOrderAssignableCarrierEnum){
        this.workOrderAssignableCarrierEnum = workOrderAssignableCarrierEnum;
    }
}
