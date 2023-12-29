package com.project.springflow.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.springflow.Enitity.Configure;
import com.project.springflow.Service.ConfigService;

@RestController
public class ConfigController {

    @Autowired
    private ConfigService configService;
    
    @GetMapping("/config")
    public ResponseEntity<List<Configure>> getAllConfig(){
        List<Configure> configList = configService.getAllConfigure();
        return ResponseEntity.ok(configList);
    }

    @PostMapping("/config")
    public ResponseEntity<Configure> createConfigure(@RequestBody Configure Configure) {
        Configure savedConfigure = configService.saveConfigure(Configure);
        return  ResponseEntity.status(HttpStatus.CREATED).body(savedConfigure);
    }
}
