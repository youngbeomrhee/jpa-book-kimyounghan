package jpabook.model.entity.ch6_4_다대다.양방향_02_새로운_기본_키_사용;

import javax.persistence.*;
import java.util.List;

//@Entity
public class ManyProduct {
    @Id @GeneratedValue
    @Column(name = "PRODUCT_ID")
    private Long id;

    private String name;

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
