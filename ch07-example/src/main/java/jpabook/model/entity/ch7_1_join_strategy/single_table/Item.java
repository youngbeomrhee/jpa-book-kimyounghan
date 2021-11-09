package jpabook.model.entity.ch7_1_join_strategy.single_table;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

//@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 부모 클래스에 매핑전략 기술
@DiscriminatorColumn(name = "DTYPE")    // 식별자컬럼
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    private String name;
    private int price;

}
