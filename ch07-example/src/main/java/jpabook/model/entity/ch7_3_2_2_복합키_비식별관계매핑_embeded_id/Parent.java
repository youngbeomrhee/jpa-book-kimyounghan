package jpabook.model.entity.ch7_3_2_2_복합키_비식별관계매핑_embeded_id;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

//@Entity
public class Parent {
    @EmbeddedId
    private ParentID id;  // ParentId.id1과 연결

    private String name;

    public ParentID getId() {
        return id;
    }

    public void setId(ParentID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Parent{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }
}
