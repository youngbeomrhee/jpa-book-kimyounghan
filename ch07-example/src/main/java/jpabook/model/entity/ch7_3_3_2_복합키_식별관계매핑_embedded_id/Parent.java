package jpabook.model.entity.ch7_3_3_2_복합키_식별관계매핑_embedded_id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

// 부모
//@Entity
public class Parent {
    @Id @Column(name = "PARENT_ID")
    private String id;
    private String name;
}
