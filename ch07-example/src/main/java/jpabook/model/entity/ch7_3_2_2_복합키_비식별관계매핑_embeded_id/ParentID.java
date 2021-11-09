package jpabook.model.entity.ch7_3_2_2_복합키_비식별관계매핑_embeded_id;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ParentID implements Serializable {
    @Column(name = "PARENT_ID1")
    private String id1;
    @Column(name = "PARENT_ID2")
    private String id2;

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
        if (!(o instanceof ParentID)) {
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
