package jpabook.model.entity.ch6_3_일대일.ch6_3_1_주_테이블에_외래_키.단방향;

import javax.persistence.*;

//@Entity
public class SubLocker {
    @Id
    @GeneratedValue
    @Column(name = "LOCKER_ID")
    private Long id;

    private String name;
}
