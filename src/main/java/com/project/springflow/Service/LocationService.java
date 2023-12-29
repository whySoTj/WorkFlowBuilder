package com.project.springflow.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.project.springflow.Enitity.Location;
import com.project.springflow.Repository.LocationRepo;

@Service
public class LocationService {
    @Autowired
    private LocationRepo LocationRepo;   

    public List<Location> getAllLocations() {
        List<Location> locationList = LocationRepo.findAll();
        return locationList;
    }

    public Location getLocationById(int id) {
        return LocationRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found with ID: " + id));
    }

    public Location createLocation(Location location) {
        Location createLocation = LocationRepo.save(location);
        return createLocation;
    }

    public void deleteLocationById(int id) {
        LocationRepo.deleteById(id);
    }
    
}
