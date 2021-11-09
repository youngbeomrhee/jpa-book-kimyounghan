package jpabook.model.entity.ch7_2_mapped_superclass;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity {
    @Id @GeneratedValue
    private Long id;
    private String name;
}
