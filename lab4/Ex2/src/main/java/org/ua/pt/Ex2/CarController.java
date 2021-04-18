package org.ua.pt.Ex2;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CarController {

    private CarManagerService carManagerService;

    public CarController(CarManagerService carManagerService) {
        this.carManagerService = carManagerService;
    }

    @PostMapping("/create")
    public ResponseEntity<Car> createCar( @RequestBody Car c) {
        Car car = carManagerService.save(c);
        if (car != null) {
            return new ResponseEntity<>(car, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/all_cars")
    public List<Car> getAllCars() {
       return carManagerService.getAllCars();
    }

    @GetMapping("/car/{id}")
    public ResponseEntity<Car> getCarById( @PathVariable Long id) {
        Optional<Car> car = carManagerService.getCarDetails(id);
        if (car.isPresent()) {
            Car carFinal = car.get();
            return new ResponseEntity<>(carFinal, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
