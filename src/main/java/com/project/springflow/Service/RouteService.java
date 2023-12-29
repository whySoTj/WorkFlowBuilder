package com.project.springflow.Service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.springflow.Enitity.Location;
import com.project.springflow.Enitity.Route;
import com.project.springflow.Repository.RouteRepo;

@Service
public class RouteService {
    @Autowired
    private RouteRepo routeRepo;

    public List<Route> getAllRoutes() {
        return routeRepo.findAll();
    }

    public Optional<Route> getRouteById(int id) {
        return routeRepo.findById(id);
    }

    public Route addRoute(Route route) {
        return routeRepo.save(route);
    }
    public List<Location> getDestinationByOrigin(Location origin){
        return routeRepo.findDestinationsForOrigin(origin);
    }
    public Route findRouteByOriginAndDestinationIds(Integer originId, Integer destinationId) {
        return routeRepo.findRouteByOriginAndDestinationIds(originId, destinationId);
    }
}
