package com.project.springflow.Enitity;

import com.project.springflow.Enum.CarrierEnum;
import com.project.springflow.Enum.ItemTypeEnum;
import com.project.springflow.Enum.LoadTypeEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "routeCarrierTable")
public class RouteCarrier {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int routeCarrierId;
    private int capacity;
    private int cost;
    private int deliverIn;


    @ManyToOne
    @JoinColumn(name = "carrier_id")
    private Carrier carrier;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;   

    @Enumerated(EnumType.STRING)
    private CarrierEnum carrierStatus;
    
    @Enumerated(EnumType.STRING)
    private ItemTypeEnum itemType;

    @Enumerated(EnumType.STRING)
    private LoadTypeEnum loadType;
}

