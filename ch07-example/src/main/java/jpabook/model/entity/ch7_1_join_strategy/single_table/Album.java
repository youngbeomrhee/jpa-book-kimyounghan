package jpabook.model.entity.ch7_1_join_strategy.single_table;

import javax.persistence.DiscriminatorValue;

//@Entity
@DiscriminatorValue("A")    // 식별값 필수. 지정하지 않으면 기본으로 엔티티 이름(Album) 사용
public class Album extends Item {

    private String artist;
}
