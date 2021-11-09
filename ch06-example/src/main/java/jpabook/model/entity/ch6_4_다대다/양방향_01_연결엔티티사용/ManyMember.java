package jpabook.model.entity.ch6_4_다대다.양방향_01_연결엔티티사용;

import javax.persistence.*;
import java.util.List;

//@Entity
public class ManyMember {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String username;

    // 역방향
    @OneToMany(mappedBy = "member")
    private List<MemberProduct> products;

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

}
