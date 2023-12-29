package com.project.springflow.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.springflow.Repository.WorkFlowRepo;


import com.project.springflow.Enitity.WorkFlow;

@Service
public class WorkFlowService {
    @Autowired
    private WorkFlowRepo WorkFlowRepo;    

    public WorkFlow saveWorkFlow(WorkFlow workFlow) {
        return WorkFlowRepo.save(workFlow);
    }

    public List<WorkFlow> getAllWorkFlows() {
        return WorkFlowRepo.findAll();
    }    
}
