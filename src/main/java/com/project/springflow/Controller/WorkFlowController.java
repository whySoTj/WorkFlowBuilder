package com.project.springflow.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.springflow.Enitity.WorkFlow;
import com.project.springflow.Service.WorkFlowService;

@RestController
public class WorkFlowController {
    @Autowired
    private WorkFlowService workFlowService;

    @PostMapping("/workflow")
    public ResponseEntity<WorkFlow> createWorkFlow(@RequestBody WorkFlow workFlow) {
        WorkFlow savedWorkFlow = workFlowService.saveWorkFlow(workFlow);
        return new ResponseEntity<>(savedWorkFlow, HttpStatus.CREATED);
    }

    @GetMapping("/workflow")
    public ResponseEntity<List<WorkFlow>> getAllWorkFlows() {
        List<WorkFlow> workFlows = workFlowService.getAllWorkFlows();
        return new ResponseEntity<>(workFlows, HttpStatus.OK);
    }
}
