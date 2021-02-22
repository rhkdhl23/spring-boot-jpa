package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //저장안됨
@RequiredArgsConstructor
public class ItemService {

  private final ItemRepository itemRepository;

  @Transactional
  public void saveItem(Item item) {
    itemRepository.save(item);
  }

  //<< 엔티티를 변경할때는 항상 변경감지 사용 >>
  //변경 감지 기능 사용
  //merge는 null때문에 사용하면 안돼 -> 병합시 값이 없으면 'null'로 업데이트가 되서
  @Transactional
  public void updateItem(Long id, String name, int price) {
    Item item = itemRepository.findOne(id);
    item.setName(name);
    item.setPrice(price);
  }
//    findItem.change(price, name, stockQuantity);

    //set으로 다갈아버리면 안됨 setter 같은경우는 웬만하면 쓰지말자
   //값을 세팅한 다음에
//    findItem.setName(name);
//    findItem.setPrice(price);
//    findItem.setStockQuantity(stockQuantity);



    //아무것도 호출할 필요가 없음
    //Transactional에 의해 commit됨

  //조회
  public List<Item> findItems() {
    return itemRepository.findAll();
  }
  public Item findOne(Long itemId) {
    return itemRepository.findOne(itemId);
  }
}
