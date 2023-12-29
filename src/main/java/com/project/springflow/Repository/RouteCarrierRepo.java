package com.project.springflow.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.springflow.Enitity.RouteCarrier;

@Repository
public interface RouteCarrierRepo extends JpaRepository<RouteCarrier, Integer> {
    
}
