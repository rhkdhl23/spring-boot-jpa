package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

  private final ItemService itemService;

  @GetMapping(value = "/items/new")
  public String createForm(Model model) {
    model.addAttribute("form", new BookForm());
    return "items/createItemForm";
  }

  @PostMapping(value = "/items/new")
  public String create(BookForm form) {

    Book book = new Book();
    book.setName(form.getName());
    book.setPrice(form.getPrice());
    book.setStockQuantity(form.getStockQuantity());
    book.setAuthor(form.getAuthor());
    book.setIsbn(form.getIsbn());

    itemService.saveItem(book);
    return "redirect:/items";
  }

  //상품 목록
  @GetMapping("/items")
  public String list(Model model){
    List<Item> items = itemService.findItems();
    model.addAttribute("items", items);
    return "items/itemList";
  }

  //상품 수정
  @GetMapping("items/{itemId}/edit")
  public String updateItemForm(@PathVariable("itemId") Long itemId, Model model){
    Book item = (Book) itemService.findOne(itemId);

    BookForm form = new BookForm();
    form.setId(item.getId());
    form.setName(item.getName());
    form.setPrice(item.getPrice());
    form.setStockQuantity(item.getStockQuantity());
    form.setAuthor(item.getAuthor());
    form.setIsbn(item.getIsbn());

    model.addAttribute("form", form);
    return "items/updateItemForm";
  }

  //상품 수정
  @PostMapping(value = "/items/{itemId}/edit")
  public String updateItem(@ModelAttribute("form") BookForm form) {
    itemService.updateItem(form.getId(), form.getName(), form.getPrice());
    return "redirect:/items";
  }
    // << 준영속 엔티티 >>
    // : 영속성 컨텍스트가 더는 관리하지 않는 엔티티 -> Book
    //Book 객체는
    //이미 DB에 한번 저장되어서 식별자가 존재함
    //이렇게 임의로 만들어낸 엔티티도 기존 식별자를 가지고 있으면 준영속 엔티티로 볼 수 있음


    //<< 준영속 엔티티가 갖고 있는 문제 >>
    //jpa가 관리를 하지 않음 그래서 값을 바꿔치기해도 update가 이루어지지 않음

    //<< 준영속 엔티티 수정하는 2가지 방법 >>
    //1. 변경 감지 기능 사용 -> Dirty Checking
    //2. 병함(merge) 사용  <- 웬만하면 사용 하지마세용용

//    @PostMapping(value = "/items/{itemId}/edit")
//    public String updateItem(@ModelAttribute("form") BookForm form) {
//      itemService.updateItem(form.getId(), form.getName(), form.getPrice());
//      return "redirect:/items";
//    }
}
