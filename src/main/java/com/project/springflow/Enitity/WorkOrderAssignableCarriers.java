package com.project.springflow.Enitity;

import com.project.springflow.Enum.ItemTypeEnum;
import com.project.springflow.Enum.LoadTypeEnum;
import com.project.springflow.Enum.WorkOrderAssignableCarrierEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class WorkOrderAssignableCarriers {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int workOrderId;
    private int carrierId;
    private int cost;
    private int routeId;
    private int capacity;
    private String carrierName;

    
    @Enumerated(EnumType.STRING)
    private LoadTypeEnum loadTypeEnum;

    @Enumerated(EnumType.STRING)
    private ItemTypeEnum itemTypeEnum;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private WorkOrderAssignableCarrierEnum workOrderAssignableCarrierEnum = WorkOrderAssignableCarrierEnum.UNASSIGNED;

}
