package jpabook.model.entity.ch6_3_일대일.ch6_3_2_대상_테이블에_외래_키.양방향;

import javax.persistence.*;

// 일대일 : 주 테이블에 외래 키를 둠(객체지향 개발자들이 선호. 반대의 경우는 데이터베이스 개발자들이 선호), 단방향 예제 코드
//@Entity
public class MainMember {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String username;

    @OneToOne(mappedBy = "member") // 양방향이므로 연관관계의 주인을 설정.
    // Locker 테이블이 외래 키를 가지고 있으므로 Locker.member가 연관관계의 주인
    private SubLocker locker;
}
