package jpabook.model.entity.ch6_1_다대일.단방향;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

// Member는 Member.team으로 Team 엔티티를 참조할 수 있지만 반대로 팀에는 회원을 참조하는 필드가 없다
// 따라서 Member와 Team은 단방향 연관관계다
@Entity
public class ToOneTeam {
    public ToOneTeam() {
    }

    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    public ToOneTeam(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
