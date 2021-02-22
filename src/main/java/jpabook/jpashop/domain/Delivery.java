package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Setter
public class Delivery {

  @Id
  @GeneratedValue
  @Column(name = "delivery_id")
  private Long id;

  @OneToOne(mappedBy = "delivery", fetch = LAZY)
  private Order order;

  @Embedded
  private Address address;

  //주의 -> ordinal : 1,2,3,4 숫자로 돌아감, 그러니 절대 쓰지말고 STRING으로 쓰기
  @Enumerated(EnumType.STRING)
  private DeliveryStatus status; //READY(배송준비), COMP(배송)
}
