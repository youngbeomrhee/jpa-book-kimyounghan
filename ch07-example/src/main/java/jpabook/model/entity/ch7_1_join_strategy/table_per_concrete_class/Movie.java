package jpabook.model.entity.ch7_1_join_strategy.table_per_concrete_class;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

//@Entity
@DiscriminatorValue("M")    // 식별값
public class Movie extends Item {

    private String director;    // 감독
    private String actor;   // 배우
}
