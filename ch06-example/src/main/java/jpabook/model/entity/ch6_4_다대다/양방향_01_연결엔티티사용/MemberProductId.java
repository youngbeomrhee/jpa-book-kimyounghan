package jpabook.model.entity.ch6_4_다대다.양방향_01_연결엔티티사용;

import java.io.Serializable;

// 회원상품 식별자 클래스
public class MemberProductId implements Serializable {
    private String member;  // MemberProduct.member와 연결
    private String product;  // MemberProduct.product와 연결

    // hashCode and equals
    @Override
    public boolean equals(Object o) {
        return true;
    }

}
