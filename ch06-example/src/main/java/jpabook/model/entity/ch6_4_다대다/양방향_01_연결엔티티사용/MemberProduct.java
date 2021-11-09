package jpabook.model.entity.ch6_4_다대다.양방향_01_연결엔티티사용;

import javax.persistence.*;

//@Entity
@IdClass(MemberProductId.class)
public class MemberProduct {
    @Id
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private ManyMember member;  // MemberProductId.member와 연결

    @Id
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private ManyProduct product;  // MemberProductId.product와 연결

    private int orderAmount;

    // ...
}
