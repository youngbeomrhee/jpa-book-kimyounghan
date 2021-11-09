package jpabook.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {
    public Team() {
    }

    public Team(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    @Column(name = "TEAM_ID")
    private String id;

    private String name;

    @OneToMany(mappedBy = "team")   // 연관관계의 주인인 Member.team
    private List<Member> members = new ArrayList<Member>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member> getMembers() {
        return members;
    }

    // 양방향 연관관계 편의 메소드를 양쪽에 작성할 경우 무한루프에 빠지지 않도록 주의
    public void addMember(Member member) {
        this.members.add(member);
        if (member.getTeam() != this) { // 무한루프에 빠지지 않도록 체크
            member.setTeam(this);
        }
    }
}
