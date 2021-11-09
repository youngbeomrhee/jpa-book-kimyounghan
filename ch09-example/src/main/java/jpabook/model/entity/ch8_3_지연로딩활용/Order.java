package jpabook.model.entity.ch8_3_지연로딩활용;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ORDER")
public class Order {
    @Id
    @Column(name = "ID")
    private Long id;

    // 연관관계 매핑
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")   // 생략시 필드명_참조테이블컬럼명 으로 생성. 현재 기준으로는 team_TEAM_ID로 생성됨
    private Member member;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
