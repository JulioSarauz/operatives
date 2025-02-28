package com.coac.operador.controller.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PaymentDto {
    private String carId;
    private Double amount;
}
