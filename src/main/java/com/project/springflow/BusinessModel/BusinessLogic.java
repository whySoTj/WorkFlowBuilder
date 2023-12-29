package com.project.springflow.BusinessModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.springflow.Enitity.RouteCarrier;
import com.project.springflow.Enitity.WorkOrder;
import com.project.springflow.Enitity.WorkOrderAssignableCarriers;
import com.project.springflow.Enum.CarrierEnum;
import com.project.springflow.Enum.ConfigEnum;
import com.project.springflow.Enum.LoadTypeEnum;
import com.project.springflow.Repository.RouteCarrierRepo;
import com.project.springflow.Repository.WorkOrderAssignableCarrierRepo;
import com.project.springflow.Repository.WorkOrderRepo;

@Service
public class BusinessLogic {
    @Autowired
    private WorkOrderRepo workOrderRepo;

    @Autowired
    private RouteCarrierRepo routeCarrierRepo;

    @Autowired
    private WorkOrderAssignableCarrierRepo workOrderAssignableCarrierRepo;

    public List<RouteCarrier> listOfCarrier(int workOrderId) {
    WorkOrder workOrder = workOrderRepo.findById(workOrderId)
    .orElseThrow(()->new NoSuchElementException("No WorkOrder Found"+workOrderId));
    List<RouteCarrier>carrierList=routeCarrierRepo.findAll();
    List<RouteCarrier>filteredList = new ArrayList<>();
    
    for (RouteCarrier routeCarrier : carrierList) {
        if (workOrder.getRoute().getRouteId()==routeCarrier.getRoute().getRouteId()
            && routeCarrier.getCarrierStatus() == CarrierEnum.AVAILABLE
            && routeCarrier.getItemType() == workOrder.getItemType()
            && routeCarrier.getLoadType() == workOrder.getWorkFlow().getLoadType()
            && ((routeCarrier.getLoadType() == LoadTypeEnum.FCL)
            ||(routeCarrier.getLoadType() == LoadTypeEnum.LCL 
            && routeCarrier.getCapacity()>=workOrder.getCapacity()))) {
                System.out.println((routeCarrier.getLoadType() == LoadTypeEnum.FCL));
                System.out.println((routeCarrier.getLoadType() == LoadTypeEnum.LCL 
            && routeCarrier.getCapacity()>=workOrder.getCapacity())+"lcl");
            filteredList.add(routeCarrier);            
        }        
    }

    return filteredList;

    }

    public List<RouteCarrier> findWorkFlowConfig (int workOrderId){
        WorkOrder workOrder = workOrderRepo.findById(workOrderId)
        .orElseThrow(()->new NoSuchElementException("No WorkOrder Found"+workOrderId));
        List<RouteCarrier> carrierList = listOfCarrier(workOrderId);
        if (workOrder.getWorkFlow().getConfiguration().getConfiguration()== ConfigEnum.AUTOMATIC) {
            return sortedCarrierByCost(carrierList);
        }
            return carrierList;
    }

    public List<RouteCarrier> sortedCarrierByCost(List<RouteCarrier> filteredList){
        Collections.sort(filteredList, Comparator.comparingInt(f -> f.getCost()));
        return filteredList;
    }
    
    public void workOrderMapCarrier(List<RouteCarrier> carrierList,int workOrderId){
        for (RouteCarrier routeCarrier : carrierList) {
            workOrderAssignableCarrierRepo.save( WorkOrderAssignableCarriers.builder()
                                          .carrierId(routeCarrier.getCarrier()
                                          .getCarrierId()).workOrderId(workOrderId)
                                          .cost(routeCarrier.getCost())
                                          .carrierName(routeCarrier.getCarrier().getCarrierName()).build());

        }
    }
}
