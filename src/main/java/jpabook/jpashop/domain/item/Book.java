package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B") //저장이 될때 구분
@Getter
@Setter
public class Book extends Item {

  private Long id;
  private String author; //저자
  private String isbn;

  private String name;
  private int price;
  private int stockQuantity;
}
