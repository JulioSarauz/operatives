package com.coac.operador.data;

import com.coac.operador.data.model.Car;
import com.coac.operador.data.utils.Consts;
import com.coac.operador.data.utils.SearchCriteria;
import com.coac.operador.data.utils.SearchOperation;
import com.coac.operador.data.utils.SearchStatement;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CarRepository {

    private final CarJpaRepository repository;

    public List<Car> getCars() {
        return repository.findAll();
    }

    public Car getById(Long carId) {
        return repository.findById(carId).orElse(null);
    }

    public Car save(Car car) {
        return repository.save(car);
    }

    public void delete(Car car) {
        repository.delete(car);
    }

    public List<Car> search(String codCar, Long bookId, Long items, Double price) {
        SearchCriteria<Car> spec = new SearchCriteria<>();

        if (StringUtils.isNotBlank(codCar)) {
            spec.add(new SearchStatement(Consts.CODCAR, codCar, SearchOperation.MATCH));
        }
        if (bookId != null) {
            spec.add(new SearchStatement(Consts.BOOKID, bookId, SearchOperation.EQUAL));
        }
        if (price != null) {
            spec.add(new SearchStatement(Consts.PRICE, price, SearchOperation.EQUAL));
        }
        if (items != null) {
            spec.add(new SearchStatement(Consts.ITEMS, items, SearchOperation.EQUAL));
        }

        return repository.findAll(spec);
    }

}