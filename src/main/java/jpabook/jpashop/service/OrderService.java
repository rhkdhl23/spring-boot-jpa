package jpabook.jpashop.service;


import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final MemberRepository memberRepository;
  private final ItemRepository itemRepository;

  //한번에 하나의 상품만 주문 가능

  //주문

  /**
   * 주문
   */
  //데이터 변경하기 때문에
  //주문하는 회원 식별자, 상품 식별자, 주문 수량 정보를 받아서 실제 주문 엔티티를 생성한 후 저장
  @Transactional
  public Long order(Long memberId, Long itemId, int count) {

    //엔티티 조회
    Member member = memberRepository.findOne(memberId);
    Item item = itemRepository.findOne(itemId);

    //배송정보 생성
    Delivery delivery = new Delivery();
    delivery.setAddress(member.getAddress());
    delivery.setStatus(DeliveryStatus.READY);

    //주문상품 생성
    //하나만 넘기도록 제안해둠
    OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
    //주문 생성
    Order order = Order.createOrder(member, delivery, orderItem);

    //주문 저장
    //orderitem이랑 delivery가 자동으로 케스케이드됨
    orderRepository.save(order);
    return order.getId();
  }

  //취소

  /**
   * 주문 취소
   */
  //주문 식별자를 받아 주문 엔티티를 조회한 후 주문 엔티티에 주문 취소를 요청함
  //도메일 모델 패턴 : 엔티티가 비즈니스 로직을 가지고 객체 지향의 특성을 적극 활용하는 것
  //트잰잭션 스크립트 패턴 : 엔티티에는 비즈니스 로직이 거의 없고, 서비스 계층에서 대부분의 비즈니스 로직을 처리하는 것
  @Transactional
  public void cancelOrder(Long orderId) {
    //주문 엔티티 조회
    Order order = orderRepository.findOne(orderId);
    //주문 취소
    order.cancel();
  }

  //검색
  /**
   * 주문 검색
   */
//  OrderSearch라는 검색 조건을 가진 객체로 주문 엔티티를 검색함
//  검색
  public List<Order> findOrders(OrderSearch orderSearch){
    return orderRepository.findAllByString(orderSearch);
  }

}
