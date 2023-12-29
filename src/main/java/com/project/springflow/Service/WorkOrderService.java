package com.project.springflow.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.springflow.Enitity.WorkOrder;
import com.project.springflow.Repository.WorkOrderRepo;

import java.util.List;

@Service 
public class WorkOrderService {

    @Autowired
    private WorkOrderRepo workOrderRepo;
    
    public List<WorkOrder> getAllWorkOrders() {
        return workOrderRepo.findAll();
    }
    public WorkOrder createWorkOrder(WorkOrder workOrder) {
        return workOrderRepo.save(workOrder);
    }
}
