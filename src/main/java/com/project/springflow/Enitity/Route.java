package com.project.springflow.Enitity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int routeId;

    @ManyToOne
    @JoinColumn(name = "origin_id" )
    private Location origin;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    private Location destination;
    
    @Column(name = "distance_in_Km")
    private int distanceInKm;
}
