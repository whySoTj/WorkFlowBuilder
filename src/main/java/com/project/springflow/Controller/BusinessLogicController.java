package com.project.springflow.Controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.project.springflow.BusinessModel.BusinessLogic;
import com.project.springflow.Enitity.RouteCarrier;
import com.project.springflow.Enitity.WorkOrderAssignableCarriers;
import com.project.springflow.Repository.WorkOrderAssignableCarrierRepo;

@RestController
public class BusinessLogicController {
    @Autowired
    private BusinessLogic businessLogic;
    @Autowired
    private WorkOrderAssignableCarrierRepo workOrderAssignableCarrierRepo;

    @GetMapping("/list/{workOrderId}")
    public List<WorkOrderAssignableCarriers> filteredList(@PathVariable("workOrderId") int workOrderId){
        List <WorkOrderAssignableCarriers> workOrderCarriers =  workOrderAssignableCarrierRepo.findAll();
        List <WorkOrderAssignableCarriers>  filteredWorkOrderList = new ArrayList<>();

        for (WorkOrderAssignableCarriers workOrderAssignableCarriers : workOrderCarriers) {
            if(workOrderAssignableCarriers.getWorkOrderId() == workOrderId){
                filteredWorkOrderList.add(workOrderAssignableCarriers);
            }
        }
        return filteredWorkOrderList;
    }

    @GetMapping("/savedlist/{workOrderId}")
    public List<RouteCarrier> filteredLists(@PathVariable("workOrderId") int workOrderId){
         List<RouteCarrier> sortedList = businessLogic.findWorkFlowConfig(workOrderId);
        businessLogic.workOrderMapCarrier(sortedList,workOrderId);
        return sortedList;
    }
}
