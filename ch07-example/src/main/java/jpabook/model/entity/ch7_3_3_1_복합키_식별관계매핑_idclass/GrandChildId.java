package jpabook.model.entity.ch7_3_3_1_복합키_식별관계매핑_idclass;

import java.io.Serializable;

// 손자 ID
public class GrandChildId implements Serializable {
    private ChildId child;  // GrandChild.child 매핑
    private String id;  // GrandChild.id 매핑

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GrandChildId that = (GrandChildId) o;

        if (child != null ? !child.equals(that.child) : that.child != null) {
            return false;
        }
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        int result = child != null ? child.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
