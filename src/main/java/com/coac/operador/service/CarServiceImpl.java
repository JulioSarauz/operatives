package com.coac.operador.service;


import com.coac.operador.controller.model.CarDto;
import com.coac.operador.controller.model.CreateBookRequest;
import com.coac.operador.controller.model.CreateCarRequest;
import com.coac.operador.data.CarRepository;
import com.coac.operador.data.model.Car;
import com.coac.operador.facade.BooksFacade;
import com.coac.operador.facade.model.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;


@Service
@Slf4j
public class CarServiceImpl implements CarService {

    @Autowired //Inyeccion por campo (field injection). Es la menos recomendada.
    private BooksFacade booksFacade;

    @Autowired
    private CarRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<Car> getCars(String codCar, Long bookId, Long items, Double price) {

        if (StringUtils.hasLength(codCar) || bookId != null || price != null || items != null) {
            return repository.search(codCar, bookId, items, price);
        }

        List<Car> cars = repository.getCars();
        return cars.isEmpty() ? null : cars;
    }

    @Override
    public Car getCar(String carId) {
        return repository.getById(Long.valueOf(carId));
    }

    @Override
    public Boolean removeCar(String carId) {

        Car car = repository.getById(Long.valueOf(carId));

        if (car != null) {
            repository.delete(car);
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    @Override
    public Car createCar(CreateCarRequest request) {
        Book book = booksFacade.getBook(request.getBookId());
        if(book.getIsVisible() && book.getStock() > 0){
            //Otra opcion: Jakarta Validation: https://www.baeldung.com/java-validation
            if (   request != null && StringUtils.hasLength(request.getCodCar().trim())
                    && request.getBookId() != null
                    && request.getItems() >= 0
                    && request.getPrice() >= 0
            ) {

                Car car = Car.builder()
                        .codCar(request.getCodCar())
                        .bookId(request.getBookId())
                        .items(request.getItems())
                        .price(request.getPrice())
                        .build();

                return repository.save(car);
            } else {
                return null;
            }
        }else{
                return null;
        }

    }

    @Override
    public Car updateCar(String carId, String request) {

        //PATCH se implementa en este caso mediante Merge Patch: https://datatracker.ietf.org/doc/html/rfc7386
        Car car = repository.getById(Long.valueOf(carId));
        if (car != null) {
            try {
                JsonMergePatch jsonMergePatch = JsonMergePatch.fromJson(objectMapper.readTree(request));
                JsonNode target = jsonMergePatch.apply(objectMapper.readTree(objectMapper.writeValueAsString(car)));
                Car patched = objectMapper.treeToValue(target, Car.class);
                repository.save(patched);
                return patched;
            } catch (JsonProcessingException | JsonPatchException e) {
                log.error("Error updating product {}", carId, e);
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public Car updateCar(String carId, CarDto updateRequest) {
        Car car = repository.getById(Long.valueOf(carId));
        if (car != null) {
            car.update(updateRequest);
            repository.save(car);
            return car;
        } else {
            return null;
        }
    }
}
