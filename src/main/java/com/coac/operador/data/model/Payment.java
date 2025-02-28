package com.coac.operador.data.model;

import com.coac.operador.controller.model.PaymentDto;
import com.coac.operador.data.utils.Consts;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "payments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Payment{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = Consts.CARID, unique = true)
    private String carId;

    @Column(name = Consts.AMOUNT)
    private Double amount;

    public void update(PaymentDto paymentDto) {
        this.carId = paymentDto.getCarId();
        this.amount = paymentDto.getAmount();
    }
}
