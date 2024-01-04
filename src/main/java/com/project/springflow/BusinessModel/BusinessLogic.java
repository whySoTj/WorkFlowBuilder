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
import com.project.springflow.Enum.WorkOrderAssignableCarrierEnum;
import com.project.springflow.Enum.WorkOrderEnum;
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

    // All the conditions are going to get check from workOrderId and routeCarrier
    // table(routeId,carrierstatus,itemType,)
    public List<RouteCarrier> listOfCarrier(int workOrderId) {
        WorkOrder workOrder = workOrderRepo.findById(workOrderId)
                .orElseThrow(() -> new NoSuchElementException("No WorkOrder Found" + workOrderId));
        List<RouteCarrier> carrierList = routeCarrierRepo.findAll();
        List<RouteCarrier> filteredList = new ArrayList<>();

        boolean isValidCarrierFound = false;

        for (RouteCarrier routeCarrier : carrierList) {
            if (workOrder.getRoute().getRouteId() == routeCarrier.getRoute().getRouteId()
                    && routeCarrier.getCarrierStatus() == CarrierEnum.AVAILABLE
                    && routeCarrier.getItemType() == workOrder.getItemType()
                    && routeCarrier.getLoadType() == workOrder.getWorkFlow().getLoadType()
                    && ((routeCarrier.getLoadType() == LoadTypeEnum.FCL)
                            || (routeCarrier.getLoadType() == LoadTypeEnum.LCL
                                    && routeCarrier.getCapacity() >= workOrder.getCapacity()))) {
                System.out.println((routeCarrier.getLoadType() == LoadTypeEnum.FCL));
                System.out.println((routeCarrier.getLoadType() == LoadTypeEnum.LCL
                        && routeCarrier.getCapacity() >= workOrder.getCapacity()) + "lcl");
                filteredList.add(routeCarrier);
                isValidCarrierFound = true;
            }

        }
        if (!isValidCarrierFound) {
            workOrder.setWorkOrderStatus(WorkOrderEnum.CARRIERNOTFOUND);
            workOrderRepo.save(workOrder);
        }
        return filteredList;

    }

    public List<RouteCarrier> findWorkFlowConfig(int workOrderId) {
        WorkOrder workOrder = workOrderRepo.findById(workOrderId)
                .orElseThrow(() -> new NoSuchElementException("No WorkOrder Found" + workOrderId));
        List<RouteCarrier> carrierList = listOfCarrier(workOrderId);
        if (workOrder.getWorkFlow().getConfiguration().getConfiguration() == ConfigEnum.AUTOMATIC) {
            return sortedCarrierByCost(carrierList);
        } else if (workOrder.getWorkFlow().getConfiguration().getConfiguration() == ConfigEnum.FASTDELIVERY) {
            return sortedCarrierByTime(carrierList);
        }
        return carrierList;
    }

    public List<RouteCarrier> sortedCarrierByCost(List<RouteCarrier> filteredList) {
        Collections.sort(filteredList, Comparator.comparingInt(f -> f.getCost()));
        return filteredList;
    }

    public List<RouteCarrier> sortedCarrierByTime(List<RouteCarrier> filteredList) {
        Collections.sort(filteredList, Comparator.comparingInt(f -> f.getDeliverIn()));
        System.out.println(filteredList);
        return filteredList;
    }

    public void workOrderMapCarrier(List<RouteCarrier> carrierList, int workOrderId) {
        WorkOrder workOrder = workOrderRepo.findById(workOrderId)
                .orElseThrow(() -> new NoSuchElementException("No workOrder Found" + workOrderId));

        for (RouteCarrier routeCarrier : carrierList) {
            workOrderAssignableCarrierRepo.save(WorkOrderAssignableCarriers.builder()
                    .carrierId(routeCarrier.getCarrier()
                            .getCarrierId())
                    .workOrderId(workOrderId)
                    .cost(routeCarrier.getCost())
                    .carrierName(routeCarrier.getCarrier().getCarrierName())
                    .itemTypeEnum(routeCarrier.getItemType())
                    .loadTypeEnum(routeCarrier.getLoadType())
                    .routeId(routeCarrier.getRoute().getRouteId())
                    .capacity(workOrder.getCapacity())
                    .deliverIn(routeCarrier.getDeliverIn())
                    .build());
        }
    }

    public void automaticCarrierSelection(int workOrderId) {
        WorkOrderAssignableCarriers firstCarrier = null;
        List<WorkOrderAssignableCarriers> allAssingedWorkOrder = workOrderAssignableCarrierRepo.findAll();
        for (WorkOrderAssignableCarriers workOrderAssignableCarriers : allAssingedWorkOrder) {
            if (workOrderAssignableCarriers.getWorkOrderId() == workOrderId
                    && workOrderAssignableCarriers
                            .getWorkOrderAssignableCarrierEnum() == WorkOrderAssignableCarrierEnum.UNASSIGNED) {
                firstCarrier = workOrderAssignableCarriers;
                break;
            }
        }
        WorkOrder workOrder = workOrderRepo.findById(workOrderId)
                .orElseThrow(() -> new NoSuchElementException("No WorkOrder found with such ID: " + workOrderId));
        if (firstCarrier != null) {
            firstCarrier.setWorkOrderAssignableCarrierEnum(WorkOrderAssignableCarrierEnum.ASSIGNED);
            workOrderAssignableCarrierRepo.save(firstCarrier);

            if (workOrder != null) {
                // Log the current status before updating
                System.out.println("Current WorkOrder Status: " + workOrder.getWorkOrderStatus());

                // Update the status of the WorkOrder entity
                workOrder.setWorkOrderStatus(WorkOrderEnum.ASSIGNED);
                workOrder.setCarrierName(firstCarrier.getCarrierName());
                workOrder.setCost(firstCarrier.getCost());
                workOrder.setDeliverIn(firstCarrier.getDeliverIn());
                System.out.println("Setting WorkOrder Status to: " + workOrder.getWorkOrderStatus());

                // Save the WorkOrder entity to persist the changes
                WorkOrder savedWorkOrder = workOrderRepo.save(workOrder);

                // Check if the status was updated and saved properly
                if (savedWorkOrder.getWorkOrderStatus() == WorkOrderEnum.ASSIGNED) {
                    System.out.println("WorkOrder status updated successfully.");
                } else {
                    System.out.println("WorkOrder status update failed.");
                }
            } else {
                System.out.println("WorkOrder not found for ID: " + workOrderId);
            }
        } else {
            workOrder.setWorkOrderStatus(WorkOrderEnum.REJECTED);
            workOrder.setCarrierName("No Carrier Found");
            workOrder.setCost(0);
            workOrder.setDeliverIn(0);
            workOrderRepo.save(workOrder);

        }
    }

    public void rejectCarrierSelection(int workOrderId) {
        WorkOrderAssignableCarriers unassignedCarrier = null;
        WorkOrderAssignableCarriers rejectedCarrier = null;
        WorkOrder workOrder = workOrderRepo.findById(workOrderId)
                .orElseThrow(() -> new NoSuchElementException("No WorkOrder found with such ID: " + workOrderId));
        List<WorkOrderAssignableCarriers> allAssingedWorkOrder = workOrderAssignableCarrierRepo.findAll();
        for (WorkOrderAssignableCarriers workOrderAssignableCarriers : allAssingedWorkOrder) {
            if (workOrderAssignableCarriers.getWorkOrderId() == workOrderId
                    && workOrderAssignableCarriers
                            .getWorkOrderAssignableCarrierEnum() == WorkOrderAssignableCarrierEnum.ASSIGNED) {
                rejectedCarrier = workOrderAssignableCarriers;
                break;
            }
        }
        for (WorkOrderAssignableCarriers workOrderAssignableCarriers : allAssingedWorkOrder) {
            if (workOrderAssignableCarriers.getWorkOrderId() == workOrderId
                    && workOrderAssignableCarriers
                            .getWorkOrderAssignableCarrierEnum() == WorkOrderAssignableCarrierEnum.UNASSIGNED) {
                unassignedCarrier = workOrderAssignableCarriers;
                break;
            }
        }
        if (rejectedCarrier != null
                && (workOrder.getWorkFlow().getConfiguration().getConfiguration() == ConfigEnum.MANUAL)) {
            rejectedCarrier.setWorkOrderAssignableCarrierEnum(WorkOrderAssignableCarrierEnum.REJECTED);
            workOrderAssignableCarrierRepo.save(rejectedCarrier);
            workOrder.setWorkOrderStatus(WorkOrderEnum.REJECTED);
            workOrderRepo.save(workOrder);

            // workOrderAssignableCarrierRepo.deleteById(rejectedCarrier.getId());
        }
        if (rejectedCarrier != null
                && (workOrder.getWorkFlow().getConfiguration().getConfiguration() == ConfigEnum.AUTOMATIC ||
                        workOrder.getWorkFlow().getConfiguration().getConfiguration() == ConfigEnum.FASTDELIVERY)) {
            rejectedCarrier.setWorkOrderAssignableCarrierEnum(WorkOrderAssignableCarrierEnum.REJECTED);
            // workOrderAssignableCarrierRepo.save(rejectedCarrier);
            // workOrder.setWorkOrderStatus(WorkOrderEnum.REJECTED);
            // workOrderRepo.save(workOrder);
            // workOrderAssignableCarrierRepo.save(rejectedCarrier);
            workOrderAssignableCarrierRepo.deleteById(rejectedCarrier.getId());
        }
        if (!(workOrder.getWorkFlow().getConfiguration().getConfiguration() == ConfigEnum.MANUAL)) {
            automaticCarrierSelection(workOrderId);
        }
    }

    public void acceptCarrierSelection(int workOrderId) {
        WorkOrderAssignableCarriers firstCarrier = null;
        List<WorkOrderAssignableCarriers> allAssingedWorkOrder = workOrderAssignableCarrierRepo.findAll();
        for (WorkOrderAssignableCarriers workOrderAssignableCarriers : allAssingedWorkOrder) {
            if (workOrderAssignableCarriers.getWorkOrderId() == workOrderId
                    && workOrderAssignableCarriers
                            .getWorkOrderAssignableCarrierEnum() == WorkOrderAssignableCarrierEnum.ASSIGNED) {
                firstCarrier = workOrderAssignableCarriers;
                break;
            }
        }
        if (firstCarrier != null) {
            firstCarrier.setWorkOrderAssignableCarrierEnum(WorkOrderAssignableCarrierEnum.ACCEPTED);
            workOrderAssignableCarrierRepo.save(firstCarrier);

            WorkOrder workOrder = workOrderRepo.findById(workOrderId)
                    .orElseThrow(() -> new NoSuchElementException("No WorkOrder found with such ID: " + workOrderId));
            if (workOrder != null) {
                // Log the current status before updating
                System.out.println("Current WorkOrder Status: " + workOrder.getWorkOrderStatus());

                // Update the status of the WorkOrder entity
                workOrder.setWorkOrderStatus(WorkOrderEnum.ACCEPTED);
                workOrder.setCarrierName(firstCarrier.getCarrierName());
                workOrder.setCost(firstCarrier.getCost());
                workOrder.setDeliverIn(firstCarrier.getDeliverIn());
                System.out.println("Setting WorkOrder Status to: " + workOrder.getWorkOrderStatus());

                // Save the WorkOrder entity to persist the changes
                WorkOrder savedWorkOrder = workOrderRepo.save(workOrder);

                // Check if the status was updated and saved properly
                if (savedWorkOrder.getWorkOrderStatus() == WorkOrderEnum.ASSIGNED) {
                    System.out.println("WorkOrder status updated successfully.");
                } else {
                    System.out.println("WorkOrder status update failed.");
                }
            } else {
                System.out.println("WorkOrder not found for ID: " + workOrderId);
            }
        }
        // updateRouteCarrierCapacityAndStatus(firstCarrier.getRouteId(),
        // firstCarrier.getCarrierId(),
        // firstCarrier.getCapacity());
    }

    // public void updateRouteCarrierCapacityAndStatus(int routeId, int carrierId,
    // int capacity) {
    // List<RouteCarrier> updatecCarrierCapacity = routeCarrierRepo.findAll();
    // for (RouteCarrier routeCarrier : updatecCarrierCapacity) {
    // if (routeCarrier.getCarrier().getCarrierId() == carrierId &&
    // routeCarrier.getRoute().getRouteId() == routeId) {
    // if (routeCarrier.getLoadType() == LoadTypeEnum.FCL) {
    // RouteCarrier setCarrierStatus = routeCarrier;
    // setCarrierStatus.setCarrierStatus(CarrierEnum.NOTAVAILABLE);
    // routeCarrierRepo.save(setCarrierStatus);
    // } else {
    // RouteCarrier setCarrierCapacity = routeCarrier;
    // setCarrierCapacity.setCapacity(setCarrierCapacity.getCapacity() - capacity);
    // }
    // break;
    // }

    // }
    // }

    // public void rewampCarrierCapacityAndStatus(int routeCarrierId) {
    // RouteCarrier routeCarrier = routeCarrierRepo.findById(routeCarrierId)
    // .orElseThrow(() -> new NoSuchElementException("No RouteCarrier Found by such
    // Id" + routeCarrierId));

    // if (routeCarrier.getLoadType() == LoadTypeEnum.FCL) {
    // routeCarrier.setCarrierStatus(CarrierEnum.AVAILABLE);
    // } else {
    // routeCarrier.setCapacity(90);
    // }
    // routeCarrierRepo.save(routeCarrier);
    // }

    public void selectCarrierManu(int workOrderId, int carrierId) {
        List<WorkOrderAssignableCarriers> findAllAssignableCarriers = workOrderAssignableCarrierRepo.findAll();
        WorkOrderAssignableCarriers assignableCarrier = new WorkOrderAssignableCarriers();
        for (WorkOrderAssignableCarriers workOrderAssignableCarriers : findAllAssignableCarriers) {
            if (workOrderAssignableCarriers.getCarrierId() == carrierId
                    && workOrderAssignableCarriers.getWorkOrderId() == workOrderId) {
                assignableCarrier = workOrderAssignableCarriers;
                break;
            }
        }
        assignableCarrier.setWorkOrderAssignableCarrierEnum(WorkOrderAssignableCarrierEnum.ASSIGNED);
        workOrderAssignableCarrierRepo.save(assignableCarrier);

        WorkOrder workOrder = workOrderRepo.findById(workOrderId)
                .orElseThrow(() -> new NoSuchElementException("No RouteCarrier Found by such Id" + workOrderId));
        workOrder.setWorkOrderStatus(WorkOrderEnum.ASSIGNED);
        workOrder.setCarrierName(assignableCarrier.getCarrierName());
        workOrder.setCost(assignableCarrier.getCost());
        workOrder.setDeliverIn(assignableCarrier.getDeliverIn());
        workOrderRepo.save(workOrder);

    }
}
