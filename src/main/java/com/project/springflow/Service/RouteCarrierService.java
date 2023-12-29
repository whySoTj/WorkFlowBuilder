package com.project.springflow.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.springflow.Enitity.RouteCarrier;
import com.project.springflow.Repository.RouteCarrierRepo;

@Service
public class RouteCarrierService {

    @Autowired
    private RouteCarrierRepo routeCarrierRepo;

    public List<RouteCarrier> getAllRouteCarriers() {
        return routeCarrierRepo.findAll();
    }

    public RouteCarrier getRouteCarrierById(int id) {
        return routeCarrierRepo.findById(id).orElse(null);
    }

    public RouteCarrier createRouteCarrier(RouteCarrier routeCarrier) {
        // You may add additional logic/validation before saving to the repository
        return routeCarrierRepo.save(routeCarrier);
    }

    public RouteCarrier updateRouteCarrier(int id, RouteCarrier updatedRouteCarrier) {
        RouteCarrier existingRouteCarrier = routeCarrierRepo.findById(id).orElse(null);
        if (existingRouteCarrier != null) {
            // Update the existingRouteCarrier with the updated values
            existingRouteCarrier.setCapacity(updatedRouteCarrier.getCapacity());
            existingRouteCarrier.setCost(updatedRouteCarrier.getCost());
            // Set other properties if needed

            return routeCarrierRepo.save(existingRouteCarrier);
        }
        return null; // Return null if the entity with the given id is not found
    }
    
    public boolean deleteRouteCarrier(int id) {
        RouteCarrier existingRouteCarrier = routeCarrierRepo.findById(id).orElse(null);
        if (existingRouteCarrier != null) {
            routeCarrierRepo.delete(existingRouteCarrier);
            return true;
        }
        return false; // Return false if the entity with the given id is not found
    }
    
}
