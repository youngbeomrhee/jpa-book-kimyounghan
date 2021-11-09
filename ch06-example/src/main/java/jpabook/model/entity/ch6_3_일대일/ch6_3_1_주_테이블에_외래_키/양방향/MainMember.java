package jpabook.model.entity.ch6_3_일대일.ch6_3_1_주_테이블에_외래_키.양방향;

import javax.persistence.*;

// 일대일 : 주 테이블에 외래 키를 둠(객체지향 개발자들이 선호. 반대의 경우는 데이터베이스 개발자들이 선호), 단방향 예제 코드
//@Entity
public class MainMember {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String username;

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private SubLocker locker;
}
