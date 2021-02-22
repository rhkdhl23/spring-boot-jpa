package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) // junit이랑 실행할때 spring이랑 같이 실행할래?
@SpringBootTest //springboot띄운 상태에서 실행 되야 함

//데이터 변경해야하기 때문에
@Transactional
public class MemberServiceTest {

  @Autowired
  MemberService memberService;
  @Autowired
  MemberRepository memberRepository;
  @Autowired
  EntityManager em;

  /**
   * 회원가입
   **/
  @Test
//  @Rollback(false)
  public void 회원가입() throws Exception {
    //given
    Member member = new Member();
    member.setName("kim");

    //when
    Long saveId = memberService.join(member);

    //then
//    em.flush(); //member에 있는게 쿼리에 반영됨 insert문 볼 수 있음
    assertEquals(member, memberRepository.findOne(saveId));
  }

  /**
   * 중복_회원_예외
   */
  @Test(expected = IllegalStateException.class)
  public void 중복_회원_예외() throws Exception {
    //given
    Member member1 = new Member();
    member1.setName("kim");
    Member member2 = new Member();
    member2.setName("kim");

    //when
    //위에 @Test(expected = IllegalStateException.class)붙히면
    //간단하게 줄일 수 있지만 그래도..
//    memberService.join(member1);
//    try {
//      memberService.join(member2); //예외가 발생해야 함
//    } catch (IllegalStateException e) {
//      return;
//    }
    memberService.join(member1);
    memberService.join(member2);

    //then
    fail("예외가 발생해야 한다."); //코드가 돌다가 여기 오면 fail!
  }
}
