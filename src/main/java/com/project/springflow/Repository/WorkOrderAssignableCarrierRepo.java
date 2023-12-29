package com.project.springflow.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.springflow.Enitity.WorkOrderAssignableCarriers;

public interface WorkOrderAssignableCarrierRepo extends JpaRepository<WorkOrderAssignableCarriers,Integer>{
        
}
