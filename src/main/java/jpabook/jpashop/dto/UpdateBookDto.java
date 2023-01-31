package jpabook.jpashop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateBookDto {
    private String name;
    private int price;
    private int stockQuantity;
    private String author;
    private String isbn;
}
