package jpabook.model.entity.ch6_4_다대다.양방향_02_새로운_기본_키_사용;

import javax.persistence.*;
import java.util.ArrayList;
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
    private List<Order> orders = new ArrayList<Order>();

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
