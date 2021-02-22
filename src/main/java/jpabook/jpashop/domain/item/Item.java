package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity

//전략잡기 -> JOINED : 가장 정규화된 스타일 , SINGLE_TABLE : 한번에 다 때려박는것
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
public abstract class Item {

  @Id
  @GeneratedValue
  @Column(name = "itme_id")
  private Long id;

  //상속관계 매핑

  private String name;
  private int price;
  private int stockQuantity;

  @ManyToMany(mappedBy = "items")
  private List<Category> categories = new ArrayList<Category>();

  //비즈니스 로직
  //재고가 줄고 늘고

  /**
   * 재고 수량 stock을 증가하는 로직
   */
  public void addStock(int quantity) {
    this.stockQuantity += quantity;
  }

  /**
   * 재고 수량 stock을 감소 로직
   */
  public void removeStock(int quantity) {
    //남은 수량계산
    int restStock = this.stockQuantity - quantity;

//    //만약에 남은 수량이 0보다 작으면? 난리남
//    if (restStock < 0) {
//      throw new NotEnoughStockException("need more stock");
//    }

    this.stockQuantity = restStock; //남은 수량으로 세팅
  }
}
