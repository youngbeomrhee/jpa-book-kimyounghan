package jpabook.model.entity.ch6_4_다대다.양방향;

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

    @ManyToMany
    @JoinTable(
            name = "MEMBER_PRODUCT",    // 연결테이블 지정
            joinColumns = @JoinColumn(name = "MEMBER_ID"),  // 현재 방향인 회원과 매핑할 조인 컬럼 정보 지정
            inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID")   // 반대 방향인 상품과 매핑할 조인 컬럼 정보를 지정
    )
    private List<ManyProduct> products = new ArrayList<ManyProduct>();

    // 연관관계 편의 메소드 추가
    public void addProduct(ManyProduct product) {
        products.add(product);
        product.getMembers().add(this);
    }

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

    public List<ManyProduct> getProducts() {
        return products;
    }

    public void setProducts(List<ManyProduct> products) {
        this.products = products;
    }
}
