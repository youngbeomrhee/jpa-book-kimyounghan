package jpabook.model.entity.ch6_1_다대일.양방향;

import javax.persistence.*;

// 양방향은 외래 키가 있는 쪽이 연관관계의 주인이다
// 주인만 외래키를 관리(수정, 삭제)할 수 있고 그 외에는 조회만 할 수 있다
//@Entity
public class ManyMember {
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String username;

    // 연관관계 매핑
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")   // 생략시 필드명_참조테이블컬럼명 으로 생성. 현재 기준으로는 team_TEAM_ID로 생성됨
    private OneTeam team;

    // 연관관계 설정
    public void setTeam(OneTeam team) {
        // 기존 팀과의 관계를 제거
        if(this.team != null) {
            this.team.getMembers().remove(this);
        }
        this.team = team;
        // 양방향 연관관계이므로 team에도 member를 추가하는 "연관관계 편의 메소드"
        // 양방향 연관관계 편의 메소드를 양쪽에 작성할 경우 무한루프에 빠지지 않도록 주의
        if (!team.getMembers().contains(this)) {
            team.getMembers().add(this);
        }
        team.getMembers().add(this);
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