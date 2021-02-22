package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
//가급적이면 데이터 변경은 transactional안에서 실행되어야 함
//@Transactional : 영속성 컨텍스트
//readOnly=true: 데이터의 변경이 없는 읽기 전용 메서드에민 사용됨
//              영속성 컨텍스트를 플러시 하지 않으므로 약간의 성능 향상!(읽기전용에는 다 적용)
//              DB Driver가 지원하면 DB에서 성능 향상
@RequiredArgsConstructor
public class MemberService {
  //ctrl + shift + t -> Test만들어짐 : MemberServiceTest.java

  //필드 주입
  // 생성자 Injection(주입) 많이 사용, 생성자가 하나일 경우 생략 가능
  private final MemberRepository memberRepository;

  /**
   * 회원가입
   */
  @Transactional //쓰기 전용이므로 넣어주어야 함
  public Long join(Member member){ //그냥 member객체를 넘기도록

    validateDuplicateMember(member); //중복 회원 검증
    memberRepository.save(member);
    return member.getId();
  }

  //중복회원 검증 로직
  private void validateDuplicateMember(Member member) {
    List<Member> findMembers =memberRepository.findByName(member.getName());
    if (!findMembers.isEmpty()) {
      throw new IllegalStateException("이미 존재하는 회원입니다.");
    }
  }

  /**
   * 회원 전체 조회
   */
  public List<Member> findMembers() {
    return memberRepository.findAll();
  }

  public Member findOne(Long memberId) {
    return memberRepository.findOne(memberId);
  }
}
