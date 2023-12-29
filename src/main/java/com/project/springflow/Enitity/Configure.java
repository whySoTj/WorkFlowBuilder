package com.project.springflow.Enitity;

import com.project.springflow.Enum.ConfigEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Configure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int configId;
    
    @Enumerated(EnumType.STRING)
    private ConfigEnum configuration;

}
