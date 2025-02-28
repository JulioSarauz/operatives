package com.coac.operador.facade.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Book {
    private Long id;
    private String title;
    private String isbn;
    private String author;
    private LocalDate publicationDate;
    private Double rating;
    private Double price;
    private Integer stock;
    private Boolean isVisible;
    private Category category;
}
