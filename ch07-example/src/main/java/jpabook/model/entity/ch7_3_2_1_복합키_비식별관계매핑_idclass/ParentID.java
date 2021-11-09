package jpabook.model.entity.ch7_3_2_1_복합키_비식별관계매핑_idclass;

import java.io.Serializable;

public class ParentID implements Serializable {
    private String id1; // Parent.id1 매핑
    private String id2; // Parent.id2 매핑

    public ParentID() {}

    public ParentID(String id1, String id2) {
        this.id1 = id1;
        this.id2 = id2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ParentID parentID = (ParentID) o;

        if (id1 != null ? !id1.equals(parentID.id1) : parentID.id1 != null) {
            return false;
        }
        return id2 != null ? id2.equals(parentID.id2) : parentID.id2 == null;
    }

    @Override
    public int hashCode() {
        int result = id1 != null ? id1.hashCode() : 0;
        result = 31 * result + (id2 != null ? id2.hashCode() : 0);
        return result;
    }
}
