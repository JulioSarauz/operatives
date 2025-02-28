package com.coac.operador.controller;


import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.coac.operador.controller.model.CreatePaymentRequest;
import com.coac.operador.controller.model.PaymentDto;
import com.coac.operador.data.model.Payment;
import com.coac.operador.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Products Controller", description = "Microservicio encargado de exponer operaciones CRUD sobre productos alojados en una base de datos en memoria.")
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService service;

    @GetMapping("")
    @Operation(
            operationId = "Obtener pagos",
            description = "Operacion de lectura",
            summary = "Se devuelve una lista de todos los pagos almacenados en la base de datos.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Payment.class)))
    public ResponseEntity<List<Payment>> getPayments(
            @RequestHeader Map<String, String> headers,
            @Parameter(name = "carId", description = "codigo carrito que pago", example = "219", required = false)
            @RequestParam(required = false) String carId,
            @Parameter(name = "amount", description = "monto pagado", example = "2.99", required = false)
            @RequestParam(required = false) Double amount) {

        log.info("headers: {}", headers);
        List<Payment> payment = service.getPayments(carId, amount);

        if (payment != null) {
            return ResponseEntity.ok(payment);
        } else {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/{paymentId}")
    @Operation(
            operationId = "Obtener un pago",
            description = "Operacion de lectura",
            summary = "Se devuelve un pago a partir de su identificador.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Payment.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "No se ha encontrado el producto con el identificador indicado.")
    public ResponseEntity<Payment> getPayment(@PathVariable String paymentId) {
        log.info("Request received for product {}", paymentId);
        Payment payment = service.getPayment(paymentId);

        if (payment != null) {
            return ResponseEntity.ok(payment);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{paymentId}")
    @Operation(
            operationId = "Eliminar un pago",
            description = "Operacion de escritura",
            summary = "Se elimina un pago a partir de su identificador.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "No se ha encontrado el pago con el identificador indicado.")
    public ResponseEntity<Void> deletePayment(@PathVariable String paymentId) {
        Boolean removed = service.removePayment(paymentId);
        if (Boolean.TRUE.equals(removed)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("")
    @Operation(
            operationId = "Insertar un pago",
            description = "Operacion de escritura",
            summary = "Se crea un pago a partir de sus datos.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del pago a crear.",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreatePaymentRequest.class))))
    @ApiResponse(
            responseCode = "201",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Payment.class)))
    @ApiResponse(
            responseCode = "400",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "Datos incorrectos introducidos.")
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "No se ha encontrado el pago con el identificador indicado.")
    public ResponseEntity<Payment> addPayment(@RequestBody CreatePaymentRequest request) {

        Payment createdPayment = service.createPayment(request);

        if (createdPayment != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPayment);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @PatchMapping("/{paymentId}")
    @Operation(
            operationId = "Modificar parcialmente un pago",
            description = "RFC 7386. Operacion de escritura",
            summary = "RFC 7386. Se modifica parcialmente un pago.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del pago a crear.",
                    required = true,
                    content = @Content(mediaType = "application/merge-patch+json", schema = @Schema(implementation = String.class))))
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Payment.class)))
    @ApiResponse(
            responseCode = "400",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "Pago inválido o datos incorrectos introducidos.")
    public ResponseEntity<Payment> patchPayment(@PathVariable String paymentId, @RequestBody String patchBody) {

        Payment patched = service.updatePayment(paymentId, patchBody);
        if (patched != null) {
            return ResponseEntity.ok(patched);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @PutMapping("/{productId}")
    @Operation(
            operationId = "Modificar totalmente un pago",
            description = "Operacion de escritura",
            summary = "Se modifica totalmente un pago.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del pago a actualizar.",
                    required = true,
                    content = @Content(mediaType = "application/merge-patch+json", schema = @Schema(implementation = PaymentDto.class))))
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Payment.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "Producto no encontrado.")
    public ResponseEntity<Payment> updateProduct(@PathVariable String productId, @RequestBody PaymentDto body) {

        Payment updated = service.updatePayment(productId, body);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
