package com.project.springflow.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.springflow.Enitity.WorkOrderAssignableCarriers;
import com.project.springflow.Enum.WorkOrderAssignableCarrierEnum;

public interface WorkOrderAssignableCarrierRepo extends JpaRepository<WorkOrderAssignableCarriers,Integer>{

    @Query("SELECT c FROM WorkOrderAssignableCarriers c WHERE c.workOrderAssignableCarrierEnum = :status1 OR c.workOrderAssignableCarrierEnum = :status2")
public List<WorkOrderAssignableCarriers> assignedCarrierList(@Param("status1") WorkOrderAssignableCarrierEnum status1, @Param("status2") WorkOrderAssignableCarrierEnum status2);


}


