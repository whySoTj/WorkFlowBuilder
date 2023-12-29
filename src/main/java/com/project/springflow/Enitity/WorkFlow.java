package com.project.springflow.Enitity;

import com.project.springflow.Enum.LoadTypeEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WorkFlow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int workFlowId;
    private int budget;
    private String workFlowName;
    @Enumerated(EnumType.STRING)
    private LoadTypeEnum loadType;
   @ManyToOne
   @JoinColumn(name = "configure_id",referencedColumnName = "configId")
   private Configure Configuration;
}
