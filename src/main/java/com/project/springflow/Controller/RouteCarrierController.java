package com.project.springflow.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.springflow.BusinessModel.BusinessLogic;
import com.project.springflow.Enitity.RouteCarrier;
import com.project.springflow.Service.RouteCarrierService;

@RestController
@RequestMapping("/routecarriers")
public class RouteCarrierController {

    @Autowired
    private RouteCarrierService routeCarrierService;
    @Autowired
    private BusinessLogic businessLogic;

    @GetMapping
    public ResponseEntity<List<RouteCarrier>> getAllRouteCarriers() {
        List<RouteCarrier> routeCarriers = routeCarrierService.getAllRouteCarriers();
        return new ResponseEntity<>(routeCarriers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteCarrier> getRouteCarrierById(@PathVariable int id) {
        RouteCarrier routeCarrier = routeCarrierService.getRouteCarrierById(id);
        if (routeCarrier != null) {
            return new ResponseEntity<>(routeCarrier, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<RouteCarrier> createRouteCarrier(@RequestBody RouteCarrier routeCarrier) {
        RouteCarrier createdRouteCarrier = routeCarrierService.createRouteCarrier(routeCarrier);
        return new ResponseEntity<>(createdRouteCarrier, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RouteCarrier> updateRouteCarrier(@PathVariable int id,
            @RequestBody RouteCarrier routeCarrier) {
        RouteCarrier updatedRouteCarrier = routeCarrierService.updateRouteCarrier(id, routeCarrier);
        if (updatedRouteCarrier != null) {
            return new ResponseEntity<>(updatedRouteCarrier, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRouteCarrier(@PathVariable int id) {
        boolean deleted = routeCarrierService.deleteRouteCarrier(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // @GetMapping("/status/{routeCarrierId}")
    // public void updateStatus(@PathVariable("routeCarrierId") int routeCarrierId) {
    //     businessLogic.rewampCarrierCapacityAndStatus(routeCarrierId);
    // }

}