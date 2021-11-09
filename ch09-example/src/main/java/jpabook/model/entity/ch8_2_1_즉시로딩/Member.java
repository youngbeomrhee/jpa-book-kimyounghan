package jpabook.model.entity.ch8_2_1_즉시로딩;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * User: HolyEyE
 * Date: 13. 5. 24. Time: 오후 7:43
 */
//@Entity
@Table(name="MEMBER")
public class Member {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String name;

    private Integer age;

    @ManyToOne(fetch = FetchType.EAGER
        ,optional = false    // inner join으로 조회
    )
    @JoinColumn(name = "TEAM_ID"
        , insertable = false
        , updatable = false
//        nullable = false    // inner join으로 조회
    )
    private Team team;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
