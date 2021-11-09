package jpabook.model.entity.ch6_2_일대다.단방향;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class FromOneTeam {
    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    @OneToMany
    @JoinColumn(name = "TEAM_ID")   // Member 테이블의 TEAM_ID (FK)
    private List<ToManyMember> members = new ArrayList<ToManyMember>();

    public FromOneTeam(String name) {
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

    public List<ToManyMember> getMembers() {
        return members;
    }
}
