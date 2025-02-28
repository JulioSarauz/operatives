package com.coac.operador.data.model;

import com.coac.operador.controller.model.CarDto;
import com.coac.operador.data.utils.Consts;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cars")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = Consts.CODCAR)
    private String codCar;

    @Column(name = Consts.BOOKID)
    private Long bookId;

    @Column(name = Consts.ITEMS)
    private Long items;

    @Column(name = Consts.PRICE)
    private Double price;


    public void update(CarDto carDto) {
        this.codCar = carDto.getCodCar();
        this.bookId = carDto.getBookId();
        this.items = carDto.getItems();
        this.price = carDto.getPrice();
    }

}
