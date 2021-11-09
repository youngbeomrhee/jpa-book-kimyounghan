package jpabook.model.entity.ch7_1_join_strategy.join;

import javax.persistence.DiscriminatorValue;

//@Entity
@DiscriminatorValue("M")    // 식별값
public class Movie extends Item {

    private String director;    // 감독
    private String actor;   // 배우
}
