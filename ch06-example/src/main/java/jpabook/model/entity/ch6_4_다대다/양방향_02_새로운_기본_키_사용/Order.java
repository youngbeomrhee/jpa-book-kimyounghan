package jpabook.model.entity.ch6_4_다대다.양방향_02_새로운_기본_키_사용;

import javax.persistence.*;

//@Entity
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private ManyMember member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private ManyProduct product;

    private int orderAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ManyMember getMember() {
        return member;
    }

    public void setMember(ManyMember member) {
        this.member = member;
    }

    public ManyProduct getProduct() {
        return product;
    }

    public void setProduct(ManyProduct product) {
        this.product = product;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }
}
