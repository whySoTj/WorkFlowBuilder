package com.project.springflow.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.project.springflow.BusinessModel.BusinessLogic;
import com.project.springflow.Enitity.RouteCarrier;
import com.project.springflow.Enitity.WorkOrder;
import com.project.springflow.Enitity.WorkOrderAssignableCarriers;
import com.project.springflow.Enum.ConfigEnum;
import com.project.springflow.Enum.WorkOrderAssignableCarrierEnum;
import com.project.springflow.Repository.WorkOrderAssignableCarrierRepo;
import com.project.springflow.Repository.WorkOrderRepo;

@RestController
public class BusinessLogicController {
    @Autowired
    private BusinessLogic businessLogic;
    @Autowired
    private WorkOrderAssignableCarrierRepo workOrderAssignableCarrierRepo;
    @Autowired
    private WorkOrderRepo workOrderRepo;

    @GetMapping("/list/{workOrderId}")
    public List<WorkOrderAssignableCarriers> filteredList(@PathVariable("workOrderId") int workOrderId) {
        List<WorkOrderAssignableCarriers> workOrderCarriers = workOrderAssignableCarrierRepo.findAll();
        List<WorkOrderAssignableCarriers> filteredWorkOrderList = new ArrayList<>();

        for (WorkOrderAssignableCarriers workOrderAssignableCarriers : workOrderCarriers) {
            if (workOrderAssignableCarriers.getWorkOrderId() == workOrderId) {
                filteredWorkOrderList.add(workOrderAssignableCarriers);
            }
        }
        return filteredWorkOrderList;
    }

    @GetMapping("/savedlist/{workOrderId}")
    public List<RouteCarrier> filteredLists(@PathVariable("workOrderId") int workOrderId) {
        List<RouteCarrier> sortedList = businessLogic.findWorkFlowConfig(workOrderId);
        businessLogic.workOrderMapCarrier(sortedList, workOrderId);

        WorkOrder workOrder = workOrderRepo.findById(workOrderId)
        .orElseThrow(()->new NoSuchElementException("No such work order found with"+workOrderId));
        if (workOrder.getWorkFlow().getConfiguration().getConfiguration()== ConfigEnum.AUTOMATIC) {
            businessLogic.automaticCarrierSelection(workOrderId);
        }else if (workOrder.getWorkFlow().getConfiguration().getConfiguration()== ConfigEnum.FASTDELIVERY) {
            businessLogic.automaticCarrierSelection(workOrderId);
        }
        return sortedList;
    }

    @GetMapping("/reject/{workOrderId}")
    public void rejectCarrier(@PathVariable("workOrderId") int workOrderId) {
        businessLogic.rejectCarrierSelection(workOrderId);
    }

    @GetMapping("/accept/{workOrderId}")
    public void acceptCarrier(@PathVariable("workOrderId") int workOrderId) {
        businessLogic.acceptCarrierSelection(workOrderId);
    }

    @GetMapping("/selectcarrier/{workOrderId}/{carrierId}")
    public void selectCarrier(@PathVariable("workOrderId") int workOrderId, @PathVariable("carrierId") int carrierId) {
        // Use the received workOrderId and carrierId in your logic
        businessLogic.selectCarrierManu(workOrderId, carrierId);
    }

    @GetMapping("/assignedCarriers")
    public List<WorkOrderAssignableCarriers> getAssignedCarrier(){
        List<WorkOrderAssignableCarriers> assignedList = workOrderAssignableCarrierRepo.assignedCarrierList(WorkOrderAssignableCarrierEnum.ASSIGNED,WorkOrderAssignableCarrierEnum.ACCEPTED);
        return assignedList;
        // System.out.println(assignedList);

    }
    


    

}
