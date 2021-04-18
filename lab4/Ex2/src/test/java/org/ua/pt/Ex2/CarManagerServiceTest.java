package org.ua.pt.Ex2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarManagerServiceTest {

    @Mock
    private CarRepository repo;

    @InjectMocks
    private CarManagerService service;

    @Test
    public void whenCarSaved_ReturnCar() {
        Car car = new Car("Fiat","500");
        when(repo.save(car)).thenReturn(car);

        Car carReturned = service.save(car);

        verify(repo, times(1)).save(car);
        assertEquals(carReturned,car);
    }

    @Test
    public void whenGetAllCars_ReturnListOfCars() {
        ArrayList<Car> cars = new ArrayList<>();

        cars.add(new Car("Fiat", "500"));
        cars.add(new Car("Tesla", "Model X"));
        cars.add(new Car("Volkswagen", "Passat"));
        cars.add(new Car("Fiat", "Panda"));
        cars.add(new Car("Audi", "A4"));

        when(repo.findAll()).thenReturn(cars);

        List<Car> carsReturned = service.getAllCars();

        verify(repo, times(1)).findAll();
        assertEquals(carsReturned,cars);
    }


    @Test
    public void whenGetDetails_ReturnTheSpecificCar() {
        Car car = new Car("Tesla", "Model 3");

        when(repo.findByCarId(any())).thenReturn(car);

        Optional<Car> carReturned = service.getCarDetails(3L);

        verify(repo, times(1)).findByCarId(any());
        assertThat(carReturned.isPresent()).isTrue();
        assertThat(carReturned.get()).isEqualTo(car);
    }

    @Test
    public void whenGetDetailsMultiple_ReturnedTheSpecificCarsAsked() {
        Car teslaS = new Car("Tesla", "Model S");
        Car teslaX = new Car("Tesla", "Model X");

        when(repo.findByCarId(3L)).thenReturn(teslaS);
        when(repo.findByCarId(4L)).thenReturn(teslaX);

        Optional<Car> carReturned1 = service.getCarDetails(3L);
        Optional<Car> carReturned2 = service.getCarDetails(4L);

        verify(repo, times(2)).findByCarId(any());
        assertThat(carReturned1.isPresent()).isTrue();
        assertThat(carReturned2.isPresent()).isTrue();
        assertThat(carReturned1.get()).isEqualTo(teslaS);
        assertThat(carReturned2.get()).isEqualTo(teslaX);
    }

    @Test
    public void whenGetDetailsDoesntExist_ReturnEmptyOptional() {

        when(repo.findByCarId(3L)).thenReturn(null);

        Optional<Car> car = service.getCarDetails(3L);

        verify(repo, times(1)).findByCarId(3L);
        assertThat(car.isPresent()).isFalse();
    }



}