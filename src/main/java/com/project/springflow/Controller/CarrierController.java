package com.project.springflow.Controller;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.springflow.Enitity.Carrier;
import com.project.springflow.Service.CarrierService;

@RestController
@RequestMapping("/carrier")
public class CarrierController {

    @Autowired
    private  CarrierService carrierService;

    

    @GetMapping()
    public List<Carrier> getAllCarriers() {
        return carrierService.getAllCarriers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carrier> getCarrierById(@PathVariable int id) {
        Optional<Carrier> carrier = carrierService.getCarrierById(id);
        return carrier.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    public ResponseEntity<Carrier> createCarrier(@RequestBody Carrier carrier) {
        Carrier createdCarrier = carrierService.createOrUpdateCarrier(carrier);
        return new ResponseEntity<>(createdCarrier, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carrier> updateCarrier(@PathVariable int id, @RequestBody Carrier carrier) {
        Optional<Carrier> existingCarrier = carrierService.getCarrierById(id);
        if (existingCarrier.isPresent()) {
            carrier.setCarrierId(id);
            Carrier updatedCarrier = carrierService.createOrUpdateCarrier(carrier);
            return new ResponseEntity<>(updatedCarrier, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarrier(@PathVariable int id) {
        Optional<Carrier> carrier = carrierService.getCarrierById(id);
        if (carrier.isPresent()) {
            carrierService.deleteCarrier(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
