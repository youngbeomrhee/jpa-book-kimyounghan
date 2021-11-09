package jpabook.model.entity.ch6_4_다대다.양방향;

import javax.persistence.*;
import java.util.List;

//@Entity
public class ManyProduct {
    @Id @GeneratedValue
    @Column(name = "PRODUCT_ID")
    private Long id;

    private String name;

    public List<ManyMember> getMembers() {
        return members;
    }

    public void setMembers(List<ManyMember> members) {
        this.members = members;
    }

    @ManyToMany(mappedBy = "products")  // 역방향 추가
    private List<ManyMember> members;

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
}
