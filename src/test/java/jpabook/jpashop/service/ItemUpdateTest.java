package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemUpdateTest {

  @Autowired
  EntityManager em;

  @Test
  public void updateTest() throws Exception{
    Book book = em.find(Book.class, 1L);

    //TX (트랜잭션 안에선) 이름바꿔치기
    book.setName("asdfasdf");

    //TX commit이 되면 자동으로 반영되는데
    //이때 이것을 변경 감지 == dirty checking


  }
}
