package jpabook.model.entity.ch7_1_join_strategy.table_per_concrete_class;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

// 자식 엔티티마다 테이블을 만든다. 일반적으로 추천하지 않는 전략
//@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // 부모 클래스에 매핑전략 기술
@DiscriminatorColumn(name = "DTYPE")    // 식별자컬럼
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    private String name;
    private int price;

}
