package com.project.springflow.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.springflow.Enitity.Location;
import com.project.springflow.Enitity.Route;
import com.project.springflow.Service.RouteService;

@RestController
public class RouteController {
    @Autowired
    private RouteService routeService;

    @GetMapping("/route")
    public ResponseEntity<List<Route>> getAllRoutes() {
        List<Route> routes = routeService.getAllRoutes();
        return new ResponseEntity<>(routes, HttpStatus.OK);
    }

    @GetMapping("/route/destination")
    public ResponseEntity<List<Location>> getDestinationByOrigin(@RequestParam("locationId") int locationId,
            @RequestParam("city") String city) {
        Location newLocation = new Location(locationId, city);
        List<Location> routes = routeService.getDestinationByOrigin(newLocation);
        return new ResponseEntity<>(routes, HttpStatus.OK);
    }

    @GetMapping("/route/{id}")
    public ResponseEntity<Route> getRouteById(@PathVariable("id") int id) {
        Optional<Route> route = routeService.getRouteById(id);
        return route.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/getroute")
    public ResponseEntity<Route> findRouteByOriginAndDestination(
            @RequestParam("originId") Integer originId,
            @RequestParam("destinationId") Integer destinationId) {
        Route route = routeService.findRouteByOriginAndDestinationIds(originId, destinationId);
        if (route != null) {
            return new ResponseEntity<>(route, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/route")
    public ResponseEntity<Route> addRoute(@RequestBody Route route) {
        Route addedRoute = routeService.addRoute(route);
        return new ResponseEntity<>(addedRoute, HttpStatus.CREATED);
    }

}
