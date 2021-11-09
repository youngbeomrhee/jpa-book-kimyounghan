package jpabook.model.entity.ch7_3_4_비식별_관계로_구현;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

// 부모
//@Entity
public class Parent {
    @Id @GeneratedValue
    @Column(name = "PARENT_ID")
    private String id;
    private String name;
}
