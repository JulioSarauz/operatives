package com.coac.operador.data;

import java.util.List;

import com.coac.operador.data.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

interface PaymentJpaRepository extends JpaRepository<Payment, Long>, JpaSpecificationExecutor<Payment> {
    List<Payment> findByCarId(String carId);
    List<Payment> findByAmount(Double amount);
}
