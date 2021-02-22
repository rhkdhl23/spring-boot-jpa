package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

  //model이라는 객체를 통해 화면에 전달됨

  private final MemberService memberService;

  @GetMapping("/members/new")
  public String createForm(Model model) {

    model.addAttribute("memberForm", new MemberForm());
    return "members/createMemberForm";
  }

  @PostMapping("/members/new")
  public String create(@Valid MemberForm form, BindingResult result){ //오류가 담겨서 실행이 됨

    //에러가 있으면 return~
    if(result.hasErrors()){
      return "members/createMemberForm";
    }

    Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

    Member member = new Member();
    member.setName(form.getName());
    member.setAddress(address);

    memberService.join(member); //저장
    //저장이 끝나면
    return "redirect:/"; //첫번째 페이지로 넘어가게 됨
  }

  //* api를 만들때는 엔티티로 반환하면 안됨

  @GetMapping("/members")
  public String list(Model model){
    //findmembers로 모든 멤버 조회
    List<Member> members = memberService.findMembers();
    //model에 담아서 넘김
    model.addAttribute("members", members);
    return "members/memberList";
  }
}
