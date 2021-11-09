package jpabook.model.entity.ch7_3_2_2_복합키_비식별관계매핑_embeded_id;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParentTest {

    //엔티티 매니저 팩토리 생성
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
    static EntityManager em;
    static EntityTransaction tx;

    @BeforeAll
    public static void setup() {
        em = emf.createEntityManager(); //엔티티 매니저 생성
        tx = em.getTransaction(); //트랜잭션 기능 획득
        tx.begin();
    }

    @Test
    void parentSaveTest() {
        Parent parent = new Parent();
        ParentID parentId = new ParentID("myId1", "myId2");
        parent.setId(parentId);
        parent.setName("parentName");
        em.persist(parent);
    }

    @Test
    void parentSelectTest() {
        ParentID parentID = new ParentID("myId1", "myId2");
        Parent parent = em.find(Parent.class, parentID);
        System.out.println(parent);
    }

    @AfterAll
    public static void close() {
        try {
            tx.commit();//트랜잭션 커밋
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }
}