package com.project.springflow.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.springflow.Enitity.WorkOrderAssignableCarriers;
import com.project.springflow.Enum.WorkOrderAssignableCarrierEnum;

public interface WorkOrderAssignableCarrierRepo extends JpaRepository<WorkOrderAssignableCarriers,Integer>{

    @Query("SELECT c from WorkOrderAssignableCarriers c WHERE c.workOrderAssignableCarrierEnum = :status")
    public List<WorkOrderAssignableCarriers> assignedCarrierList(@Param("status") WorkOrderAssignableCarrierEnum status);

}


