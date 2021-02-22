package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

//스프링 빈으로 등록. jpa 예외를 스프링 기반 예외로 예외 변환
//스프링 빈 : MemberRepository객체
//MemberRepository객체에 스프링이 생성해준 JPA의 EntityManager가 자동으로 입력됨
@Repository
@RequiredArgsConstructor
public class MemberRepository {

  //엔티티 메니저 주입
  private final EntityManager em;

  public void save(Member member) {
    em.persist(member);
    //커밋되는 시점에 db에 반영
  }

  public Member findOne(Long id) {
    return em.find(Member.class, id);
    //단권조회 ,
  }

  //
  public List<Member> findAll() {
    return em.createQuery("select m from Member m", Member.class)
      .getResultList(); //jpql : entity객체를 대상으로 쿼리
  }

  //파라매터에 의해 특정 회원만 찾도록
  public List<Member> findByName(String name) {
    return em.createQuery("select m from Member m where m.name = :name", Member.class)
      .setParameter("name", name)
      .getResultList();
  }
}
