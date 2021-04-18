package org.ua.pt.Ex2;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class CarControllerTest {

    @Autowired
    MockMvc servlet;

    @MockBean
    CarManagerService carManagerService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void whenCreateACar_ReturnStatusOKAndJsonResponse() throws Exception {
        Car car = new Car("Tesla","Model S");
        given(carManagerService.save(any())).willReturn(car);

        ObjectMapper mapper = new ObjectMapper();
        String jsonObject = mapper.writeValueAsString(car);
        servlet.perform(MockMvcRequestBuilders.post("/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject)).
                andExpect(status().isOk()).
                andExpect(jsonPath("model").value("Model S"));

    }

    @Test
    public void whenBadRequest_ReturnBadRequest() throws Exception {
        Car car = null;
        given(carManagerService.save(any())).willReturn(car);

        ObjectMapper mapper = new ObjectMapper();
        String jsonObject = mapper.writeValueAsString(car);
        servlet.perform(MockMvcRequestBuilders.post("/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject)).
                andExpect(status().isBadRequest());
    }

    @Test
    public void whenGetAllCars_ReturnListCars() throws Exception {
        ArrayList<Car> cars = new ArrayList<Car>();

        cars.add(new Car("Fiat", "Punto"));
        cars.add(new Car("Tesla", "Model S"));
        cars.add(new Car("Volkswagen", "Passat"));

        given(carManagerService.getAllCars()).willReturn(cars);

        servlet.perform(MockMvcRequestBuilders.get("/all_cars"))
                .andExpect(jsonPath("$.*", isA(ArrayList.class)))
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$[0].maker").value("Fiat"))
                .andExpect(jsonPath("$[2].maker").value("Volkswagen"));
    }

    @Test
    public void whenCarById_ReturnSpecificCar() throws Exception {
        Car car =  new Car("Fiat", "500");
        given(carManagerService.getCarDetails(145L)).willReturn(Optional.of(car));

        servlet.perform(MockMvcRequestBuilders.get("/car/145"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("maker").value("Fiat"));
    }

    @Test
    public void whenCarDoesntExist_ReturnStatusNotFound() throws Exception {
        given(carManagerService.getCarDetails(anyLong())).willReturn(Optional.empty());

        servlet.perform(MockMvcRequestBuilders.get("/car/145"))
                .andExpect(status().isNotFound());
    }
}