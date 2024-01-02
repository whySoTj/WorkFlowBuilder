package com.project.springflow.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.springflow.Enitity.WorkOrder;
import com.project.springflow.Repository.WorkOrderRepo;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    public boolean deleteWorkOrderById(int workOrderId) {
        Optional<WorkOrder> optionalWorkOrder = workOrderRepo.findById(workOrderId);
        if (optionalWorkOrder.isPresent()) {
            workOrderRepo.deleteById(workOrderId);
            return true; // Deletion successful
        }
        return false; // WorkOrder with given ID not found
    }
}
