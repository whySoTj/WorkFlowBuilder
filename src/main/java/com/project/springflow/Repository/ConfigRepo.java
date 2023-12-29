package com.project.springflow.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.springflow.Enitity.Configure;

@Repository
public interface ConfigRepo extends JpaRepository<Configure,Integer>{
    
}
