package jpabook.model.entity.ch7_1_join_strategy.single_table;

import javax.persistence.DiscriminatorValue;
import javax.persistence.PrimaryKeyJoinColumn;

//@Entity
@DiscriminatorValue("B")    // 식별값
@PrimaryKeyJoinColumn(name = "BOOK_ID") // 조인 컬럼 재정의
public class Book extends Item {

    private String author;
    private String isbn;
}
