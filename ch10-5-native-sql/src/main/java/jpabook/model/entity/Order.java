package jpabook.model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by holyeye on 2014. 3. 11..
 */
@Entity
@SqlResultSetMapping(
    name = "OrderResults",
    entities = {
        @EntityResult(entityClass=jpabook.model.entity.Order.class, fields = {
            @FieldResult(name="id", column="order_id"),
            @FieldResult(name="quantity", column="order_quantity"),
            @FieldResult(name="item", column="order_item")
        })
    },
    columns = {
        @ColumnResult(name="item_name")
    }
)
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;      //주문 회원

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;     //주문시간

    private String item;

    private Integer quantity;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Order(Member member) {
        this.member = member;
    }

    //Getter, Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "Order{" +
            "id=" + id +
            ", orderDate=" + orderDate +
            '}';
    }
}
