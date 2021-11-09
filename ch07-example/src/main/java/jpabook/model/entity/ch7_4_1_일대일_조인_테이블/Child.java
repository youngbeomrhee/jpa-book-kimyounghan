package jpabook.model.entity.ch7_4_1_일대일_조인_테이블;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

// 자식
//@Entity
public class Child {
    @Id @GeneratedValue
    @Column(name = "CHILD_ID")
    private Long id;
    private String name;

    // 양방향으로 매핑하려면 아래 코드 추가
    @OneToOne(mappedBy = "child")
    private Parent parent;
}
