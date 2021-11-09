package jpabook.model.entity.ch6_3_일대일.ch6_3_1_주_테이블에_외래_키.양방향;

import javax.persistence.*;

//@Entity
public class SubLocker {
    @Id
    @GeneratedValue
    @Column(name = "LOCKER_ID")
    private Long id;

    private String name;

    @OneToOne(mappedBy = "locker")  // 양방향이므로 연관관계의 주인을 설정.
    // Member 테이블이 외래 키를 가지고 있으므로 Member.locker가 연관관계의 주인
    private MainMember member;
}
