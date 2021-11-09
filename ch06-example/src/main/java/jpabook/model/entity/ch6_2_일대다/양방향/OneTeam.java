package jpabook.model.entity.ch6_2_일대다.양방향;

import jpabook.model.entity.ch6_1_다대일.양방향.ManyMember;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@Entity
public class OneTeam {
    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    @OneToMany
    @JoinColumn(name = "TEAM_ID")
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
}
