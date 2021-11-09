package jpabook.model.entity.ch6_1_다대일.단방향;

import javax.persistence.*;

@Entity
public class FromManyMember {
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String username;

    public void setTeam(ToOneTeam team) {
        this.team = team;
    }

    // 연관관계 매핑
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")   // 생략시 필드명_참조테이블컬럼명 으로 생성. 현재 기준으로는 team_TEAM_ID로 생성됨
    private ToOneTeam team;

    public FromManyMember(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ToOneTeam getTeam() {
        return team;
    }
}
