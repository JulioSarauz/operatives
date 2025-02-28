package com.coac.operador.controller.model;
import com.coac.operador.facade.model.Book;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRequest {
    private String codCar;
    private Long bookId;
    private Long items;
    double price;
    //@NotNull(message = "`books` cannot be null")
    //@NotEmpty(message = "`books` cannot be empty")
    //private List<Long> books;
}
