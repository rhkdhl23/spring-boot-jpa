package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Setter
public class Category {

  @Id
  @GeneratedValue
  @Column(name = "category_id")
  private Long id;

  private String name;

  @ManyToMany
  @JoinTable(name = "category_item",
    joinColumns = @JoinColumn(name = "category_id"), //중간테이블에있는 아이디
    inverseJoinColumns = @JoinColumn(name = "item_id") //category_item테이블에 item쪽으로 들어가
  )
  private List<Item> items = new ArrayList<>();

  //부모 자식
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "parent_id")
  private Category parent;

  @OneToMany(mappedBy = "parent")
  private List<Category> child = new ArrayList<>(); //자식은 여러개 가질 수 있으니까

  //이것도 연관 관계 편의 메서드 - order처럼
  public void addChildCategory(Category child) {
    this.child.add(child);
    child.setParent(this);
  }
}
