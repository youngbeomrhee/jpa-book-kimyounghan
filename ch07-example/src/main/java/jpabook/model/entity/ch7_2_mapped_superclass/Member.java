package jpabook.model.entity.ch7_2_mapped_superclass;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;

//@Entity
//@AttributeOverride(name = "id", column = @Column(name = "MEMBER_ID"))   // 부모에게 상속받은 id속성의 컬럼명을 재정의
@AttributeOverrides({   // 복수인 경우
    @AttributeOverride(name = "id", column = @Column(name = "MEMBER_ID")),
    @AttributeOverride(name = "name", column = @Column(name = "MEMBER_NAME")),
})
public class Member extends BaseEntity {
    // ID 상속
    // Name 상속
    private String email;
}
