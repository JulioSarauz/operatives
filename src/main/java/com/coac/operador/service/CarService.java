package com.coac.operador.service;

import com.coac.operador.controller.model.CarDto;
import com.coac.operador.controller.model.CreateCarRequest;
import com.coac.operador.data.model.Car;

import java.util.List;


public interface CarService {
    List<Car> getCars(String codCar, Long bookId, Long items, Double price);

    Car getCar(String carId);

    Boolean removeCar(String carId);

    Car createCar(CreateCarRequest request);

    Car updateCar(String carId, String updateRequest);

    Car updateCar(String carId, CarDto updateRequest);
}
