package com.coac.operador.controller.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CarDto {
    private String codCar;
    private Long bookId;
    private Long items;
    double price;
}
