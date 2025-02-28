package com.coac.operador.data;

import com.coac.operador.data.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

interface CarJpaRepository extends JpaRepository<Car, Long>, JpaSpecificationExecutor<Car> {
    List<Car> findByCodCar(String codCar);

    List<Car> findByBookId(Long bookId);

    List<Car> findByItems(Long items);

    List<Car> findByPrice(Double price);
}
