package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

//값 타입은 변경 불가능하게 설계하여야 함
//@Setter 제거하고, 생성자에서 값을 모두 초기화해 변경 불가능한 클래스 만듦
//1. 실무에선 가급적 setter 사용하지 말기 (변경 포인트가 많아 유지보수가 어려움)
//2. 모든 연관관계는 자연로딩으로 설정!
//  -> 즉석로딩 EAGER은 예측과 추적하기 어려움
//  -> 그러니 무조건 실무에서 모든 연관관계는 자연로딩인 LAZY로 설정
//  -> ManyToOne은 기본이 EAGER이므로 @ManyToOne(fetch = FetchType.LAZY)로 바꾸던가 사용 말던가
//  -> OneToOne도 LAZY로 박아주기



//테이블 생성하면서 실행해보고 반복하기
//그러면서 제대로 생성되고 있나 체크하는 것이 좋음
//수정하고 정리!

@Embeddable
@Getter
public class Address {

  private String city;
  private String street;
  private String zipcode;

  //jpa 스펙상
  //엔티티나 임베디드 타입은 자바 기본 생성자를
  //public 이나 protected로 설정해야하는데데  //public 보다는 protected로 설정하는 것이 안전함
  //jpa 구현 라이브러리가 객체를 생성할 때,
  //리플렉션 같은 기술을 사용할 수 있도록 지원해야하기 때문
  protected Address() {
  }

  public Address(String city, String street, String zipcode) {
    this.city = city;
    this.street = street;
    this.zipcode = zipcode;
  }
}
