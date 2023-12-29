package com.project.springflow.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.springflow.Enitity.WorkOrder;

public interface WorkOrderRepo extends JpaRepository<WorkOrder,Integer>{
    
    
}
