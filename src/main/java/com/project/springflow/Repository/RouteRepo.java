package com.project.springflow.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.springflow.Enitity.Location;
import com.project.springflow.Enitity.Route;

public interface RouteRepo extends JpaRepository<Route,Integer> {
    @Query("SELECT obj.destination FROM Route as obj WHERE obj.origin = :origin")
    List<Location> findDestinationsForOrigin(@Param("origin") Location origin);

    @Query("SELECT r FROM Route as r WHERE r.origin.id = :originId AND r.destination.id = :destinationId")
    Route findRouteByOriginAndDestinationIds(@Param("originId") Integer originId, @Param("destinationId") Integer destinationId);


}