package com.project.springflow.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.springflow.BusinessModel.BusinessLogic;
import com.project.springflow.Enitity.RouteCarrier;
import com.project.springflow.Enitity.WorkOrder;
import com.project.springflow.Service.WorkOrderService;

import java.util.List;

@RestController
@RequestMapping("/workorder")
public class WorkOrderController {
    @Autowired
    private WorkOrderService workOrderService;

    @Autowired
    private BusinessLogic businessLogic;

    @GetMapping
    public ResponseEntity<List<WorkOrder>> getAllWorkOrders() {
        List<WorkOrder> workOrders = workOrderService.getAllWorkOrders();
        return new ResponseEntity<>(workOrders, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<WorkOrder> createWorkOrder(@RequestBody WorkOrder workOrder) {
        WorkOrder createdWorkOrder = workOrderService.createWorkOrder(workOrder);

        // List<RouteCarrier> sortedList =
        // businessLogic.findWorkFlowConfig(createdWorkOrder.getWorkOrderId());
        // businessLogic.workOrderMapCarrier(sortedList,createdWorkOrder.getWorkOrderId());
        return new ResponseEntity<>(createdWorkOrder, HttpStatus.CREATED);
    }

    @DeleteMapping("/{workOrderId}")
    public ResponseEntity<String> deleteWorkOrderById(@PathVariable int workOrderId) {
        boolean deleted = workOrderService.deleteWorkOrderById(workOrderId);
        if (deleted) {
            return new ResponseEntity<>("Work Order deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Work Order not found", HttpStatus.NOT_FOUND);
        }
    }
}
