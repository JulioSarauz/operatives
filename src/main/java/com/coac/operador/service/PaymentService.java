package com.coac.operador.service;

import java.util.List;
import com.coac.operador.controller.model.CreatePaymentRequest;
import com.coac.operador.controller.model.PaymentDto;
import com.coac.operador.data.model.Payment;


public interface PaymentService {
    List<Payment> getPayments(String carId, Double amount);

    Payment getPayment(String paymentId);

    Boolean removePayment(String paymentId);

    Payment createPayment(CreatePaymentRequest request);

    Payment updatePayment(String paymentId, String updateRequest);

    Payment updatePayment(String paymentId, PaymentDto updateRequest);
}
