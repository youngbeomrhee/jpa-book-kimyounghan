package jpabook.model.entity.ch7_1_join_strategy.join;

import javax.persistence.DiscriminatorValue;

//@Entity
@DiscriminatorValue("A")    // 식별값
public class Album extends Item {

    private String artist;
}
