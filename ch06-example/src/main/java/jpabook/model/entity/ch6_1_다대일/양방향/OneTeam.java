package jpabook.model.entity.ch6_1_다대일.양방향;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@Entity
public class OneTeam {
    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team")   // 연관관계의 주인인 Member.team
    private List<ManyMember> members = new ArrayList<ManyMember>();

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

    public List<ManyMember> getMembers() {
        return members;
    }

    // 양방향 연관관계 편의 메소드를 양쪽에 작성할 경우 무한루프에 빠지지 않도록 주의
    public void addMember(ManyMember member) {
        this.members.add(member);
        if (member.getTeam() != this) { // 무한루프에 빠지지 않도록 체크
            member.setTeam(this);
        }
    }
}
