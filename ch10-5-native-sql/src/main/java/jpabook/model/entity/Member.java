package jpabook.model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import jdk.jfr.Name;

@Entity
@SqlResultSetMapping(
    name = "memberWithOrderCount",
    entities = {@EntityResult(entityClass = Member.class)},
    columns = {@ColumnResult(name = "ORDER_COUNT")}
)
@NamedNativeQueries({
    // Named query 사용하고 결과 매핑(resultSetMapping)은 하지 않는 경우
    // MemberTest.Named_Query_사용()
    @NamedNativeQuery(
        name = "Member.memberSQL",
        query = "SELECT ID, AGE, NAME, TEAM_ID " +
            "FROM MEMBER WHERE AGE > ?",
        resultClass = Member.class
    ),
    @NamedNativeQuery(
        name = "Member.memberWithOrderCount",
        query = "SELECT M.ID, NAME, AGE, TEAM_ID, I.ORDER_COUNT " +
            "FROM MEMBER M " +
            "LEFT JOIN " +
            "   (SELECT IM.ID, COUNT(*) AS ORDER_COUNT " +
            "   FROM ORDERS O, MEMBER IM " +
            "   WHERE O.MEMBER_ID = IM.ID " +
            "   GROUP BY IM.ID) I " +
            "ON M.ID = I.ID ",
        resultSetMapping = "memberWithOrderCount"
    )
})
public class Member {
    public Member() {
    }
    public Member(String id, String username, Integer age) {
        this.id = id;
        this.username = username;
        this.age = age;
    }

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String username;

    private Integer age;

    // 연관관계 매핑
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")   // 생략시 필드명_참조테이블컬럼명 으로 생성. 현재 기준으로는 team_TEAM_ID로 생성됨
    private Team team;

    // 연관관계 설정
    public void setTeam(Team team) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Member{" +
            "id='" + id + '\'' +
            ", username='" + username + '\'' +
            ", age=" + age +
            ", team=" + team +
            '}';
    }
}
