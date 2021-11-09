package jpabook.model.entity.ch8_3_지연로딩활용;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TEAM")
public class Team {
    @Id
    @Column(name = "TEAM_ID")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
