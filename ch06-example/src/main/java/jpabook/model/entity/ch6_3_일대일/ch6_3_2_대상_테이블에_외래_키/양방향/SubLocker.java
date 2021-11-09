package jpabook.model.entity.ch6_3_일대일.ch6_3_2_대상_테이블에_외래_키.양방향;

import javax.persistence.*;

//@Entity
public class SubLocker {
    @Id
    @GeneratedValue
    @Column(name = "LOCKER_ID")
    private Long id;

    private String name;

    @OneToOne
    @JoinColumn(name = "MEMBER_ID")
    private MainMember member;
}
