package com.coac.operador.controller;


import com.coac.operador.controller.model.CarDto;
import com.coac.operador.controller.model.CreateCarRequest;
import com.coac.operador.data.model.Car;
import com.coac.operador.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Car Controller", description = "Microservicio encargado de exponer operaciones CRUD sobre carritos de compra alojados en una base de datos en memoria.")
@RequestMapping("/cars")
public class CarController {
    private final CarService service;

    @GetMapping("")
    @Operation(
            operationId = "Obtener carritos de compra",
            description = "Operacion de lectura",
            summary = "Se devuelve una lista de todos los carritos de compra almacenados en la base de datos.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class)))
    public ResponseEntity<List<Car>> getPayments(
            @RequestHeader Map<String, String> headers,
            @Parameter(name = "codCar", description = "codigo del carrito asignado", example = "1", required = false)
            @RequestParam(required = false) String codCar,
            @Parameter(name = "items", description = "numero de libros seleccionados", example = "1", required = false)
            @RequestParam(required = false) Long items,
            @Parameter(name = "price", description = "precio individual del libro", example = "10.99", required = false)
            @RequestParam(required = false) Double price,
            @Parameter(name = "bookId", description = "codigo del libro comrpado", example = "1", required = false)
            @RequestParam(required = false) Long bookId) {

        log.info("headers: {}", headers);
        List<Car> car = service.getCars(codCar, bookId, items, price);
        if(car != null) {
            return ResponseEntity.ok(car);
        } else {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/{carId}")
    @Operation(
            operationId = "Obtener un carrito de compras",
            description = "Operacion de lectura",
            summary = "Se devuelve un carrito a partir de su identificador.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "No se ha encontrado el carrito con el identificador indicado.")
    public ResponseEntity<Car> getCar(@PathVariable String carId) {
        log.info("Request received for product {}", carId);
        Car car = service.getCar(carId);

        if (car != null) {
            return ResponseEntity.ok(car);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{carId}")
    @Operation(
            operationId = "Eliminar un carrito",
            description = "Operacion de escritura",
            summary = "Se elimina un pago a partir de su identificador.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "No se ha encontrado el carrito con el identificador indicado.")
    public ResponseEntity<Void> deletePayment(@PathVariable String carId) {
        Boolean removed = service.removeCar(carId);
        if (Boolean.TRUE.equals(removed)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("")
    @Operation(
            operationId = "Insertar un carrito",
            description = "Operacion de escritura",
            summary = "Se crea un carrito a partir de sus datos.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del carrito a crear.",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateCarRequest.class))))
    @ApiResponse(
            responseCode = "201",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class)))
    @ApiResponse(
            responseCode = "400",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "Datos incorrectos introducidos.")
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "No se ha encontrado el carrito con el identificador indicado.")
    public ResponseEntity<Car> addCar(@RequestBody CreateCarRequest request) {

        Car createdCar = service.createCar(request);

        if (createdCar != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCar);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @PatchMapping("/{carId}")
    @Operation(
            operationId = "Modificar parcialmente un carrito",
            description = "RFC 7386. Operacion de escritura",
            summary = "RFC 7386. Se modifica parcialmente un carrito.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del carrito a crear.",
                    required = true,
                    content = @Content(mediaType = "application/merge-patch+json", schema = @Schema(implementation = String.class))))
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class)))
    @ApiResponse(
            responseCode = "400",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "Pago inválido o datos incorrectos introducidos.")
    public ResponseEntity<Car> patchCar(@PathVariable String carId, @RequestBody String patchBody) {

        Car patched = service.updateCar(carId, patchBody);
        if (patched != null) {
            return ResponseEntity.ok(patched);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @PutMapping("/{carId}")
    @Operation(
            operationId = "Modificar totalmente un carrito",
            description = "Operacion de escritura",
            summary = "Se modifica totalmente un carrito.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del carrito a actualizar.",
                    required = true,
                    content = @Content(mediaType = "application/merge-patch+json", schema = @Schema(implementation = CarDto.class))))
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "Producto no encontrado.")
    public ResponseEntity<Car> updateCar(@PathVariable String carId, @RequestBody CarDto body) {

        Car updated = service.updateCar(carId, body);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
