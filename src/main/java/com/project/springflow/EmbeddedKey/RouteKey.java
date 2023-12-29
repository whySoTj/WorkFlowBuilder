package com.project.springflow.EmbeddedKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Embeddable
public class RouteKey implements Serializable {
    private String origin;
    private String destination;

    // Constructors, getters, setters, equals, and hashCode methods
}

