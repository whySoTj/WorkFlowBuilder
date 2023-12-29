package com.project.springflow.Enitity;

import com.project.springflow.Enum.ItemTypeEnum;
import com.project.springflow.Enum.WorkOrderEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
public class WorkOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int workOrderId;  
    private int cost;
    private int capacity;


    @Enumerated(EnumType.STRING)
    @Builder.Default
    // private WorkOrderEnum workOrderStatus= WorkOrderEnum.PENDING;
    private WorkOrderEnum workOrderStatus = WorkOrderEnum.UNASSIGNED;

    @Enumerated(EnumType.STRING)
    private ItemTypeEnum itemType;
    
    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    @ManyToOne
    @JoinColumn(name = "workFlow_id")
    private WorkFlow workFlow;
//    @JoinColumn(name = "workFlow_id",referencedColumnName = "workFlowId")
    
}
