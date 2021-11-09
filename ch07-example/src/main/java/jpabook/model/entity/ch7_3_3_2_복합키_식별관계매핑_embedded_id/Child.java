package jpabook.model.entity.ch7_3_3_2_복합키_식별관계매핑_embedded_id;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

// 자식
//@Entity
public class Child {
    @EmbeddedId
    private ChildId id;

    @MapsId("parentId")     // ChildId.parentId 매핑
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    public Parent parent;

    private String name;
}
