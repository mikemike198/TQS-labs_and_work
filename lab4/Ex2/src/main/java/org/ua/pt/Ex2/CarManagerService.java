package org.ua.pt.Ex2;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarManagerService {

    CarRepository carRepository;

    public CarManagerService(CarRepository carRepository){
        this.carRepository = carRepository;
    }

    Car save(Car car) {
        Car carToReturn = carRepository.save(car);
        return carToReturn;
    }

    List<Car> getAllCars() {
        List<Car> cars = carRepository.findAll();
        return cars;
    }

    Optional<Car> getCarDetails(Long id){
        Car car = carRepository.findByCarId(id);
        Optional<Car> opt = Optional.ofNullable(car);
        return opt;
    }

}
