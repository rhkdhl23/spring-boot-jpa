package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

  @Id
  @GeneratedValue
  @Column(name = "member_id")
  private Long id;

  private String name; //회원명

  @Embedded //내장타입을 포함했다라는
  private Address address;

  @OneToMany(mappedBy = "member") //일대다 관계 - 하나의 회원이 여러개 상품 주문하기때문에
  private List<Order> orders = new ArrayList<>();

}
