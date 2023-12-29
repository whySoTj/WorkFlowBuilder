package com.project.springflow.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.springflow.Enitity.Carrier;
import com.project.springflow.Repository.CarrierRepo;

@Service
public class CarrierService {

    @Autowired
    private  CarrierRepo CarrierRepo;
    
    public List<Carrier> getAllCarriers() {
        return CarrierRepo.findAll();
    }

    public Optional<Carrier> getCarrierById(int id) {
        return CarrierRepo.findById(id);
    }

    public Carrier createOrUpdateCarrier(Carrier carrier) {
        return CarrierRepo.save(carrier);
    }

    public void deleteCarrier(int id) {
        CarrierRepo.deleteById(id);
    }
}
