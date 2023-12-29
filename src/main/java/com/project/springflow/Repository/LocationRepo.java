package com.project.springflow.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.project.springflow.Enitity.Location;

public interface LocationRepo extends JpaRepository<Location, Integer> {

    // Custom queries or methods if needed
}
