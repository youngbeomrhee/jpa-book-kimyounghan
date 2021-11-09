package jpabook.model.entity.ch6_2_일대다.양방향;

import javax.persistence.*;

// 사실상 다대일 양방향 매핑
//@Entity
public class ManyMember {
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String username;

    // 일대다 단방향 매핑 반대편에 다대일 단방향 매핑을 추가
    // 이때 일대다 단방향 매핑과 같은 TEAM_ID 외래 키 컬럼을 매핑
    // 둘 다 같은 키를 관리하므로 문제가 발생할 수 있기 때문에 insertable = false, updatable = false로 읽기만 가능하도록 설정
    // 이 방법은 일대다 양방향 매핑이라기보다는 일대다 단방향 매핑 반대편에 다대일 단방향 매핑을 일기 전용으로 추가해서 일대다 양방향처럼 보이도록 하는 방법
    // 따라서 일대다 단방향 매핑이 가지는 단점을 그대로 가지므로 될 수 있으면 다대일 양방향 매핑을 사용
    @ManyToOne
    @JoinColumn(name = "TEAM_ID", insertable = false, updatable = false)
    private OneTeam team;

    // 연관관계 설정
    public void setTeam(OneTeam team) {
        // ...
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

    public OneTeam getTeam() {
        return team;
    }
}