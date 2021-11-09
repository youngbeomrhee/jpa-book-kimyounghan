package jpabook.model.entity.ch7_3_2_2_복합키_비식별관계매핑_embeded_id;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

//@Entity
public class Child {
    @Id
    private String id;

    @ManyToOne
    @JoinColumns({  // 아래처럼 @JoinColumn의 name 속성과 referencedColumnName이 같은 경우에는 referencedColumnName은 생략해도 무방
        @JoinColumn(name = "PARENT_ID1", referencedColumnName = "PARENT_ID1"),
        @JoinColumn(name = "PARENT_ID2", referencedColumnName = "PARENT_ID2")
    })
    private Parent parent;
}
