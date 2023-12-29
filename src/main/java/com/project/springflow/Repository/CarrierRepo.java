package com.project.springflow.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.springflow.Enitity.Carrier;

@Repository
public interface CarrierRepo extends JpaRepository<Carrier, Integer> {
    // You can add custom query methods if needed
}