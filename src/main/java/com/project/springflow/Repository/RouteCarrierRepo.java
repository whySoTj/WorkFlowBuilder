package com.project.springflow.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.springflow.Enitity.RouteCarrier;
import com.project.springflow.Enum.ItemTypeEnum;

@Repository
public interface RouteCarrierRepo extends JpaRepository<RouteCarrier, Integer> {
    @Query("SELECT c from RouteCarrier c WHERE c.itemType = :itemType AND c.route.routeId = :routeId" )
    List<RouteCarrier> findCarrierByItemTypeAndRouteId (@Param("itemType")ItemTypeEnum itemType, @Param("routeId") int routeId); 
}
