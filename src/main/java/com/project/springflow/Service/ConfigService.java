package com.project.springflow.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.springflow.Enitity.Configure;
import com.project.springflow.Repository.ConfigRepo;

@Service
public class ConfigService {

    @Autowired
    private  ConfigRepo configRepo;

    public List<Configure> getAllConfigure(){
        List<Configure> configList = configRepo.findAll();
        return configList;
    } 
    public Configure saveConfigure(Configure Configure) {
        Configure savedConfig = configRepo.save(Configure);
        return savedConfig;
    }

}
   