package jpabook.model.entity.ch6_4_다대다.단방향;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@Entity
public class FromManyMember {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String username;

    @ManyToMany
    @JoinTable(
            name = "MEMBER_PRODUCT",    // 연결테이블 지정
            joinColumns = @JoinColumn(name = "MEMBER_ID"),  // 현재 방향인 회원과 매핑할 조인 컬럼 정보 지정
            inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID")   // 반대 방향인 상품과 매핑할 조인 컬럼 정보를 지정
    )
    private List<ToManyProduct> products = new ArrayList<ToManyProduct>();

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

    public List<ToManyProduct> getProducts() {
        return products;
    }

    public void setProducts(List<ToManyProduct> products) {
        this.products = products;
    }
}
