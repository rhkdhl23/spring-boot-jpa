package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

  private final EntityManager em;

  //상품 저장
  //item은 저장할때까지 id값이 없기때문에
  //id값이 없다는 것 : 새로 생성한 객체
  //
  public void save(Item item){
    if(item.getId() == null){
      em.persist(item); //신규 등록
    }else{
      em.merge(item); //업데이트
      //merge호출됨
    }
  }

  //하나 조회
  public Item findOne(Long id) {
    return em.find(Item.class, id);
  }

  //여러 찾음
  public List<Item> findAll(){
    return em.createQuery("select i from Item i", Item.class)
      .getResultList();
  }
}
