package com.coac.operador.data;

import com.coac.operador.data.utils.Consts;
import com.coac.operador.data.utils.SearchCriteria;
import com.coac.operador.data.utils.SearchOperation;
import com.coac.operador.data.utils.SearchStatement;
import com.coac.operador.data.model.Payment;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.DoubleRange;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@RequiredArgsConstructor
public class PaymentRepository {

    private final PaymentJpaRepository repository;

    public List<Payment> getPayments() {
        return repository.findAll();
    }

    public Payment getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Payment save(Payment product) {
        return repository.save(product);
    }

    public void delete(Payment product) {
        repository.delete(product);
    }

    public List<Payment> search(String carId, Double amount) {
        SearchCriteria<Payment> spec = new SearchCriteria<>();

        if (StringUtils.isNotBlank(carId)) {
            spec.add(new SearchStatement(Consts.CARID, carId, SearchOperation.MATCH));
        }
        if (amount != null) {
            spec.add(new SearchStatement(Consts.AMOUNT, amount, SearchOperation.EQUAL));
        }

        return repository.findAll(spec);
    }
}