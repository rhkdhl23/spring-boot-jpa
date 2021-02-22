package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

  @Id
  @GeneratedValue
  @Column(name = "order_id")
  private Long id;

  //다대일 관계 (order <-> member)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member; //주문 회원

  //mappedBy : 양방향 매핑을 할 때 JPA에 주인이 아니라는 것 알려주기 위해 이용함
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) //orderItem에다가 데이터만 넣어두고 order저장하면 같이 저장됨
  private List<OrderItem> orderItems = new ArrayList<>();

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY) //delivery값만 셋팅해두면, order 저장할 때, delivery도 같이 퍼시스트해줌
  @JoinColumn(name = "delivery_id")
  private Delivery delivery; //배송정보

  private LocalDateTime orderDate; //주문시간

  @Enumerated(EnumType.STRING)
  private OrderStatus status; //주문상태 [ORDER, CANCEL]

  //연관관계 편의 메서드
  //양방향일때 쓰기 좋음
  public void setMember(Member member) {
    this.member = member;
    member.getOrders().add(this);
  }

  //아래 코드를 간단하게 줄인것이 위에 setMember코드
//  public static void main(String[] args){
//    Member member = new Member();
//    Order order = new Order();
//
//    member.getOrders().add(order);
//    order.setMember(member);
//  }

  public void addOrderItem(OrderItem orderItem) {
    orderItems.add(orderItem);
    orderItem.setOrder(this);
  }

  public void setDelivery(Delivery delivery) {
    this.delivery = delivery;
    delivery.setOrder(this);
  }

  //생성 메서드
  //생성할때부터 createOrder로 부터 호출
  //여기에다가 완결
  //주문 생성 메서드니까 여기서 다잡을 수 있음
  public static Order createOrder(Member member, Delivery delivery,OrderItem... orderItems) {
    //셋팅
    Order order = new Order();
    order.setMember(member);
    order.setDelivery(delivery);

    for (OrderItem orderItem : orderItems) {
      order.addOrderItem(orderItem);
    }

    //주문 시간 정보
    order.setStatus(OrderStatus.ORDER);
    order.setOrderDate(LocalDateTime.now());
    return order;
  }

  //비즈니스 로직

  /**
   * 주문 취소
   */
  public void cancel() {
    //배송이 이미 되버림 -> 그럼 취소 불가능         //COMP : 배송완료
    if (delivery.getStatus() == DeliveryStatus.COMP) {
      throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
    }

    //상태를 여기서 바꿔주고
    this.setStatus(OrderStatus.CANCEL);

    //cancel치면 아이템의 재고를 원복 시켜줌
    for (OrderItem orderItem : orderItems) {
      orderItem.cancel();
    } //orderitem에도 모두 cancel
  }

  //조회로직

  /**
   * 전체 주문 가격 조회
   */
  public int getTotalPrice() {
    int totalPrice = 0;
    for (OrderItem orderItem : orderItems) {
      totalPrice += orderItem.getTotalPrice();
    }
    return totalPrice;
  }
}
